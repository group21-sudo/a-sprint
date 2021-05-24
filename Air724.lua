module(..., package.seeall)
-- 导入所需的库
require "pins"
require "pm"
require "scanCode"
require "utils"
require "common"
require "http"
require "testUartSentFile"

-- 显示屏参数定义
local WIDTH, HEIGHT = disp.getlcdinfo()
local DEFAULT_WIDTH, DEFAULT_HEIGHT = 320, 240

-- GPIO定义及设置
local getGpio9Fnc = pins.setup(pio.P0_9)
local getGpio10Fnc = pins.setup(pio.P0_10)

-- 相关参数定义
local stu_id
local msg
local status

-- 串口ID,1对应uart1
-- 如果要修改为uart2，把UART_ID赋值为2即可
local UART_ID = 1

-- 帧头类型以及帧尾
local CMD_SCANNER, CMD_GPIO, CMD_PORT, FRM_TAIL = 1, 2, 3, string.char(0xC0)

-- 串口读入数据缓冲区
local rdbuf = ""

-- 串口发送数据缓冲区
local responsestr ="sendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsendsend"

-- GPIO定义
local setgpio12 = pins.setup(pio.P0_12, 0)
local getGpio12Fnc = pins.setup(pio.P0_12)

-- 串口读数据函数
function read()
    rdbuf = ""
    -- 底层core中，串口收到数据时：
    -- 如果接收缓冲区为空，则会以中断方式通知Lua脚本收到了新数据；
    -- 如果接收缓冲器不为空，则不会通知Lua脚本
    -- 所以Lua脚本中收到中断读串口数据时，每次都要把接收缓冲区中的数据全部读出，这样才能保证底层core中的新数据中断上来，此read函数中的while语句中就保证了这一点
    while true do
        rdbuf = uart.read(UART_ID, "*l") -- 读取到结束字符\n或者阻塞发送
        if not rdbuf or string.len(rdbuf) == 0 then
            -- log.info("read error", "no data or len == 0")
            sys.wait(20) -- 等待对方写入数据
            -- log.info("recv from arduino",rdbuf)
            -- sys.wait(20)
        else
            -- 读到数据
            break
        end
    end
end

-- 串口写数据函数
function write(s)
    -- log.info("begin write to arduino:", s)
    sys.wait(20)
    uart.write(UART_ID, s .. "\r\n") -- 向 UART_ID 串口写入数据
end

-- 注册串口读函数
uart.on(UART_ID, "recv", read)

-- 注册串口写函数
uart.on(UART_ID, "send", write)

-- 设置串口启动所需参数
uart.setup(UART_ID, 115200, 8, uart.PAR_NONE, uart.STOP_1)

-- HTTP回调函数
local function cbFnc(result, prompt, head, body)
    if result and body then -- 请求成功
        --log.info("stu_id", json.decode(body)['stu_id'])
        --log.info("msg", json.decode(body)['msg'])
        --log.info("status", json.decode(body)['status'])
        -- 从json中获取相应数据
        stu_id = json.decode(body)['stu_id']
        msg = json.decode(body)['msg']
        status = json.decode(body)['status']
    end
    disp.clear() -- 清显示屏
    disp.setcolor(0XFB3C) -- 设置颜色
    -- 设置显示的字和位置
    disp.puttext(common.utf8ToGb2312("学号："), 0, 0) 
    disp.puttext(common.utf8ToGb2312(stu_id), 40, 0)
    disp.puttext(common.utf8ToGb2312("状态："), 0, 30)
    disp.puttext(common.utf8ToGb2312(msg), 40, 30)
    if status == 1 then
        disp.puttext(common.utf8ToGb2312("识别信息成功"), 0, 60)
    end
    -- 刷新显示屏
    disp.update()
end

-- 新建线程
sys.taskInit(function()
    while true do
        -- local setgpio12=pins.setup(pio.P0_12,0)
        -- local getGpio12Fnc = pins.setup(pio.P0_12)
        -- 保证Air724不异常重启
        log.info("12", getGpio12Fnc())
        -- 如果检测到进的一端的红外是低电平信号
        if getGpio9Fnc() == 0 then
            sys.wait(10)
            if getGpio9Fnc() == 0 then
                while getGpio9Fnc() == 0 do end
                while getGpio10Fnc() == 1 do end
                while getGpio10Fnc() == 0 do end
                -- log.info("进入一个", "进入一个")
                http.request("GET",
                             "https://group21.cychenye.com/updateSeatsNumberServlet?password=group21password&op=1&number=1",
                             nil, nil, nil, 3000, nil)
            end
        end
        -- 如果检测到出的一端的红外是低电平信号
        if getGpio10Fnc() == 0 then
            sys.wait(10)
            if getGpio10Fnc() == 0 then
                while getGpio10Fnc() == 0 do end
                while getGpio9Fnc() == 1 do end
                while getGpio9Fnc() == 0 do end
                -- log.info("出去一个", "出去一个")
                http.request("GET",
                             "https://group21.cychenye.com/updateSeatsNumberServlet?password=group21password&op=1&number=-1",
                             nil, nil, nil, 3000, nil)
            end
        end
        sys.wait(50)
    end
end)

-- 新建线程
sys.taskInit(function()
    while true do
        read() -- 读取串口数据
        --log.info("sys.taskInit  recv rdbuf:", rdbuf)
        -- log.info("rdbuf:toHex()", rdbuf:toHex())
        if string.len(rdbuf) > 100 then
            --log.info("recv begin http ok", "recv begin http ok")
            -- HTTP请求
            http.request("GET",
                         "https://group21.cychenye.com/faceSearchServlet", nil,
                         nil, nil, 3000, cbFnc)
            -- 串口写回、通知对方继续操作
            write(responsestr)
        end
        sys.wait(100)
    end
end, ...)
