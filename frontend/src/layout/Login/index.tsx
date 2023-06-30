// import './style.module.scss'
import styles from './style.module.scss'
import {Link, useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import React, {useEffect, useRef, useState} from "react";

// css中这样写可以只影响局部的元素，但也会有全局的东西，css的元素选择器还是会影响全局
interface LoginDto {
    account: string
    password: string
}

interface LoginResponseDto {
    account: string;
    randomString: string
    tag: number
    username:string
}

const Login = () => {
    const nav = useNavigate()
    const location=useLocation()
    const [loginDto, setLogin] = useState<LoginDto>({
        password: "",
        account: "",
    })

    // const [account, setAccount] = useState('');
    // const [password, setPassword] = useState('');

    let jsonData = location.state?.data
    useEffect(() => {
        try {
          if (jsonData) {
            const objectData = JSON.parse(jsonData);
            setLogin({
                account: objectData.account,
                password: ''
            })
          }
        }
        catch (error) {
          console.error('JSON 解析错误:', error);
        }
    }, [jsonData]);






const loginButtonOnClick=() => {
                        axios.post<LoginResponseDto>('/login', loginDto).then(e => {//loginDto就是把这个变量直接发送到后端了
                            localStorage.setItem("randomString", e.data.randomString)
                            localStorage.setItem("tag", e.data.tag.toString())
                            localStorage.setItem("username",  e.data.username)
                            localStorage.setItem("account", e.data.account)
                            nav("/")
                        }).catch(e => {
                            let data=e.response.data
                            let details=data.title+data.detail
                            alert(details)
                        })
                    }
const passwordInput = useRef(null);
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
                <h2>登录</h2>
                <div className={styles.inputBox}>
                    <input type="text" placeholder="账号" value={loginDto.account}
                           onKeyDown={event => {
                               if(event.key==='Enter'){
                                   if(loginDto.password===''){
                                       alert('请输入您的密码')
                                   }
                                   else {
                                       loginButtonOnClick()
                                   }
                               }
                           }}
                           onChange={e => {
                               setLogin({
                                   password: loginDto.password,
                                   account: e.target.value,
                               })
                           }}
                /></div>
                <div className={styles.inputBox}><input type="password" placeholder="密码"
                    onKeyDown={(event)=>{
                                if(event.key==='Enter'){
                                    loginButtonOnClick()
                                }
                    }}
                    onChange={e => {
                        setLogin({
                            password: e.target.value,
                            account: loginDto.account,
                        })
                    }}
                    ref={passwordInput}
                /></div>
                <div className={styles.inputBox}>
                    <input type="submit" value="登入" className={styles.btn}
                            onClick={loginButtonOnClick}/>
                </div>
                <div className={styles.group}>
                    <Link to={'/register'}>
                        <a
                        >注册
                        </a>
                    </Link>
                </div>
            </div>
        </section>
    </div>
}

export default Login;