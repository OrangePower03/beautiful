// import './style.module.scss'
import styles from './style.module.scss'
import {Link, useNavigate} from "react-router-dom";
import { useState} from "react";
import axios from "axios";
import {message} from "antd";

// css中这样写可以只影响局部的元素，但也会有全局的东西，css的元素选择器还是会影响全局
interface LoginDto {
    account: string
    password: string
}

interface RegisterDto {
    username: string
    account: string
    password: string
    confirmedPassword: string
}

const Register = () => {
    const nav = useNavigate()
    const [registerDto, setRegisterDto] = useState<RegisterDto>({
        account:"",
        password:"",
        username:"",
        confirmedPassword:"",
    })



    const RegisterButtonOnClick =() => {
                            if (registerDto.confirmedPassword!==registerDto.password)
                                alert("两次输入密码不一致")
                            else if(registerDto.password===''||registerDto.confirmedPassword==='')
                                alert("密码为空")
                            else if(registerDto.username==='')
                                alert("账号为空")
                            else if(registerDto.account==='')
                                alert("用户名为空")
                            else {
                                axios.post('/register',registerDto).then(
                                    e => {//loginDto就是把这个变量直接发送到后端了
                                        console.log(e.data)
                                        console.log('账号'+e.data["account"])
                                        console.log('用户名'+e.data["name"])
                                        const jsonData = JSON.stringify(e.data)
                                        console.log(jsonData)

                                        message.success(e.data["name"]+"恭喜您注册成功，快快登录吧！")
                                        nav("/login",{ state: { data: jsonData } })
                                    }
                                ).catch(
                                    error => {
                                        const detail=error.response.data.title+error.response.data.detail
                                        alert(detail)
                                    }
                                )
                            }

                        }

    return <div>
        <section>
            <div className={styles.container}>
                <div className={styles.scene}>
                    <div className={styles.layer} data-depth-x="-0.5" data-depth-y="0.25">

                    </div>
                    <div className={styles.layer} data-depth-x="0.15">

                    </div>
                    <div className={styles.layer} data-depth-x="0.25">

                    </div>
                    <div className={styles.layer} data-depth-x="-0.25"></div>
                </div>
            </div>
            <div className={styles.login} data-depth-x="-0.25">
                <h2>注册</h2>
                <div className={styles.inputBox}>
                    <input type="text" placeholder="用户名"
                           onChange={e=>{
                        // setRegisterDto({
                        //     ...registerDto,
                        //     account: e.target.value,
                        // })
                        setRegisterDto({
                            // const newJson = {...RegisterList}
                            //...是javascript中的对象解构语法等价于
                            // const newJson = {}
                            // newJson.var1 = RegisterList.var1
                            // newJson.var2 = RegisterList.var2
                            // ...
                            // newJson.varn = RegisterList.varn
                            ...registerDto,
                            username: e.target.value,
                        })
                    }}
                    onKeyDown={event=>{
                            if (event.key==='Enter') {
                                RegisterButtonOnClick()
                            }
                        }}/>
                </div>
                <div className={styles.inputBox}>
                    <input type="text" placeholder="账号" onChange={e=>{
                        setRegisterDto({
                            ...registerDto,
                            account: e.target.value,
                        })
                    }}
                    onKeyDown={event=>{
                            if (event.key==='Enter') {
                                RegisterButtonOnClick()
                            }
                        }}/>
                </div>
                <div className={styles.inputBox}>
                    <input type="password" placeholder="密码"
                        onKeyDown={event=>{
                            if (event.key==='Enter') {
                                RegisterButtonOnClick()
                            }
                        }}
                        onChange={e=>{
                            setRegisterDto({
                                ...registerDto,
                                password: e.target.value,
                            })
                        }}
                    />
                </div>
                <div className={styles.inputBox}>
                    <input type="password" placeholder="确认密码"
                        onKeyDown={event=>{
                            if (event.key==='Enter') {
                                RegisterButtonOnClick()
                            }
                        }}
                        onChange={e=>{
                            setRegisterDto({
                                ...registerDto,
                                confirmedPassword: e.target.value,
                            })
                        }}
                    />
                </div>
                <div className={styles.inputBox}>
                    <input
                        type="submit"
                        value="注册账号！"
                        className={styles.btn}
                        onClick={RegisterButtonOnClick}
                        //todo 这里要加点逻辑
                        // 绑定当前对象
                    />
                    <Link to={'/login'}>
                        <a className={styles.return}
                        >返回登录界面
                        </a>
                    </Link>

                </div>
            </div>
        </section>
    </div>
}

export default Register