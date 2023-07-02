import {Menu} from "antd";
import {Header} from "antd/es/layout/layout";
import React, {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const MyHeader = ()=>{
    const navigate = useNavigate()
    const [artwork,setArtwork]=useState<ArtworkDto[]>()
    return <Header>
        {/*<div style={{float:'left',color:'white'}} >用户名</div>*/}
        <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['2']}
            items={[
                {
                    label:localStorage.getItem("username"),
                    onClick:()=>{
                        window.location.href = "user"
                    },
                    key:'-2',
                },
                {
                    label:'登出',
                    onClick:()=>{
                      localStorage.removeItem("randomString")
                      window.location.href = "/login"
                    },
                    key:'-1',
                },
                {
                    label:'搜索主页',
                    key:'2',
                    onClick:()=>{
                        window.location.href = "/"
                    },
                },
                {
                    label:'上传',
                    key:'3',
                    onClick:()=>{
                          window.location.href='/a/artwork'
                    },
                },
                {
                    label:'个人上传',
                    key:'4',
                    onClick: ()=>{
                        axios.get(`/artwork/user?username=${localStorage.getItem('username')}`).
                        then(e=>{
                            setArtwork(e.data)
                            navigate(`/s/artwork?name=${localStorage.getItem('username')}&category=user`);
                        }).catch(error=> {
                                console.log('获取个人上传失败')
                            }
                        )
                    }
                },
            ]}
        />
    </Header>
}
export default MyHeader