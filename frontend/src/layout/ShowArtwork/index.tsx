import React, {useEffect} from "react";
import {useState} from 'react';
import {Input} from 'antd';
import {Image} from 'antd';

const {Search} = Input;
import './show.css'
import Intro from "./Intro";
import ChangeRecord from "./ChangeRecord";
import CelebrityImage from "./CelebrityImage";
import MyTooltip from "../SearchArtwork/MyTooltip";
import MySelect from "../SearchArtwork/MySelect";
import MyHeader from "../MyHeader";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import axios from "axios";

interface ArtworkshowDto {
    id: number
    atitle: string
    avatar: string
    intro: string
    ip: string
    kind: string
    resourceAddress: string
    time: number
    celebritys: {
        actid: number,
        name: string,
        title: number,
        avatar:string,
    }[]
}


interface CelebritytitleDto {
    tid: number
    name: string
}

interface CelebrityDto{
    src:string,
    title:string,
    name:string,
}

export default function Show() {
    const{aid}=useParams()
    const nav=useNavigate()
    let [searchParams, setSearchParams] = useSearchParams()
    const [celebrity, setCelebrity] = useState<CelebrityDto[]>([]);
    const [artworkDto, setArtworkDtoList] = useState<ArtworkshowDto>({
        intro:'',
        id:0,
        kind:'',
        atitle:'',
        ip:'',
        avatar:'',
        resourceAddress:'',
        time:0,
        celebritys:[],
    })
    const [celebrityTitleList, setCelebrityTitleList] = useState<CelebritytitleDto[]>([])
    const config={
        headers:{

            token: localStorage.getItem("token")
        }
    }
    useEffect(() => {
        //axios.get就是往后端发送一个get请求，请求的url就是你的第一个参数
        axios.get(`http://localhost:8080/artwork/${aid}`,config).then(e => {
            console.log(e.data)
            setArtworkDtoList(e.data)
            //.then是异步请求的处理部分，就是等后端发来数据以后再做处理的部分
            // e.data返回的是一个哈希表数组2333，因为有可能返回多个结果
            //如果想要append要先创建一个变量，加完以后再全部赋值进去
        })
    }, [])

    useEffect(() => {
        axios.get<CelebritytitleDto[]>('/title',config).then(e => {
            console.log(e.data)
            setCelebrityTitleList(e.data)
        })
    }, [])

    useEffect(() => {
        const updatedCelebrityList = artworkDto.celebritys.map(celebrity => {
            const titleObj = celebrityTitleList.find(title   => title.tid === celebrity.title);

            return {
                src: celebrity.avatar,
                title: titleObj ? titleObj.name : '',
                name: celebrity.name
            };
        });
        console.log(updatedCelebrityList)
        setCelebrity(updatedCelebrityList);
    }, [artworkDto,celebrityTitleList]);//不能保证后端发送的数据顺序是固定的，所以要在celebrityTitleList更新后，要把所有涉及celebrityTitleList的内容全部更新一遍。

    let artworkName = artworkDto.atitle
    let artworkImageSrc = artworkDto.avatar
    let ip = artworkDto.ip
    let kind = artworkDto.kind
    let URL = [
        {
            url: artworkDto.resourceAddress,
            name: 'TMDB'
        },
    ]
    let time=new Date(artworkDto.time*1000)
    let outputTime=time.getFullYear().toString()
    if(time.getMonth()<9){
        outputTime+='-0'+(time.getMonth()+1).toString()
    }
    else{
        outputTime+='-'+(time.getMonth()+1).toString()
    }
    if(time.getDate()<10){
        outputTime+='-0'+(time.getDate().toString())
    }
    else {
        outputTime+='-'+(time.getDate().toString())
    }

    return (
        <div id={"container"} className={"element"}>
            <MyHeader/>
            {/*作品栏*/}
            <div id={"mainShowBoard"}>
                <div id={"mainShow"}>
                    <br/>
                    <div id={"name"}>
                        {artworkName}
                    </div>
                    <div style={{display: 'flex',marginBottom:5}}>
                        <div id={"photo"} style={{
                            height: '24%',
                            width: '18%',
                            marginRight:20
                        }}>
                            <Image
                                src={artworkImageSrc}
                                width="100%"
                                height="100%"
                            />
                        </div>
                        <div id={"intro"}>
                            <p className={"titles"}><strong>作品简介：</strong></p>
                            <Intro text={artworkDto.intro}/>
                        </div>
                    </div>
                    <div style={{marginBottom:20}}>
                        <div style={{width:'50%',display:'inline-grid'}}>
                            <div>
                                <strong className={"titles"}>系列作品:&nbsp;&nbsp;</strong>{ip}
                            </div>

                            <strong className={"titles"}>资源地址：</strong>
                            <div>
                                {URL.map((url, index) => (
                                    <div key={index}>
                                        <a href={url.url} target={"_blank"}>{url.name}</a> <br/>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div style={{display:'inline-grid',width:'50%'}}>
                            <div><strong className={"titles"}>
                                类型:&nbsp;&nbsp;</strong>{kind}
                            </div>
                            <div> <strong className={"titles"} style={{}}>
                                上映时间：</strong>
                                {outputTime}
                            </div>
                        </div>
                    </div>

                    <h3 id={"staffInfo"}>
                        <strong className={"titles"}>演职人员:</strong> <br/>
                        <CelebrityImage images={celebrity}/>
                    </h3>

                </div>
            </div>
        </div>
    );
}