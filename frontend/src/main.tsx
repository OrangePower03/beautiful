import React, {ReactNode} from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import axios from "axios";
import './index.scss'

//如果在axios里面不写前面的http则会自动加上
axios.defaults.baseURL = "http://localhost:8080"

console.log(window.location.href)
if (localStorage.getItem("token") === null && !window.location.href.endsWith('/login')) {
    window.location.href = '/login'
}

const TestComponent0 = (state: { children:ReactNode  }) => {
    return <div>
        <div>up</div>
        <div>{state.children}</div>
        <div>down</div>
    </div>
}

const TestComponent = (state: { children: (aaaa:string)=> ReactNode }) => {
    return <div>
        <div>up</div>
        <div>{state.children("dfgnm")}</div>
        <div>down</div>
    </div>
}

const T2 = (arg:string)=>{ return <div>aasa{arg}</div>}

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <React.StrictMode>
        {/*<TestComponent0>*/}
        {/*    {<>*/}
        {/*        <div>asdasd</div>*/}
        {/*        <div>asdasd</div>*/}
        {/*    </>}*/}
        {/*</TestComponent0>*/}
        {/*<div children={<div children={"asdasdasd"}/>}/>*/}
        {/*<div children={<><div children={"asdasdasd"}/><div children={"dddd"}/></>}/>*/}
        {/*<div children={<><div>asdasdasd</div><div>dddd</div></>}/>*/}
        {/*<div>*/}
        {/*    <div>asdasdasd</div>*/}
        {/*    <div>dddd</div>*/}
        {/*</div>*/}
        <App/>
    </React.StrictMode>,
)
