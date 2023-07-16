import {Link, useLocation, useNavigate, useSearchParams} from "react-router-dom";
import {Button, Card, Col, Row, Space, Image, Popconfirm} from "antd";
import React, {useEffect, useState} from "react";
import axios from "axios";
import MyHeader from "../MyHeader";
import './index.css';
import MyTooltip from "../SearchArtwork/MyTooltip";


const SearchArtworkResult = () => {
    const nav=useNavigate()
    let [searchParams, setSearchParams] = useSearchParams()
    // const array = useSearchParams();
    // let searchParams = array[0]
    //跟上面等价
    //searchParams是一个奇怪的哈希表

    const [artworkDtoList, setArtworkDtoList] = useState<ArtworkDto[]>([])
    //<>是泛型，ArtworkDto是一个泛型参数
    const config={
        headers:{
            token: localStorage.getItem("token")
        }
    }


    //useEffect函数被当作构造函数使用，第一个参数是一个函数，没有参数，第二个参数是一个数组
    useEffect(() => {
        //axios.get就是往后端发送一个get请求，请求的url就是你的第一个参数
        axios.get(`http://localhost:8080/artwork?name=${searchParams.get("name")}&category=${searchParams.get("category")}`, config).
        then(e => {
            setArtworkDtoList(e.data)//.then是异步请求的处理部分，就是等后端发来数据以后再做处理的部分，e.data返回的是一个哈希表数组2333，因为有可能返回多个结果
            //如果想要append要先创建一个变量，加完以后再全部赋值进去
        }).catch(error=>{
            const detail=error.response.data.title+'\n'+error.response.data.detail
            alert(detail)
        })
    }, [searchParams])

    const onDelete = (e: ArtworkDto) => {
        axios.delete(`http://localhost:8080/artwork/${e.id}`,config).then(_ => {
            setArtworkDtoList(artworkDtoList.filter(ee => ee.id !== e.id))//.then后面就是刷新前端内容
        })
    }
    //{}是一个插值表达式
    interface CategoryLabels {
        [key: string]: string;
    }

    const getCategoryLabel = (categoryValue: string): string => {
        const categoryLabels: CategoryLabels = {
            'all': '作品全局搜索',
            'staff':'演职人员'
        };

        return categoryLabels[categoryValue] || categoryValue;

    };


    return <div>
        <MyHeader/>
        <h2>根据 {searchParams.get("name")} 和 {getCategoryLabel(searchParams.get("category") ?? '')} 的搜索结果是：</h2>
        {/*将artworkDtoList的每一个值都拿出来映射成一个Card*/}
        {artworkDtoList.map(e => <Card
            style={{margin: '5px'}}
            title={e.title}
            extra={
                localStorage.getItem("tag") === '1' ?
                     <div style={{textAlign:"right"}}>
                            <strong style={{fontSize:20,float:'left'}}>
                            {e.atitle}</strong>
                        <Space>
                            <Link to={`/e/Artwork/${e.id}`}>
                                <a href="#">Edit</a>
                            </Link>
                            <Popconfirm
                                title="Delete the task"
                                description="Are you sure to delete this task?"
                                onConfirm={() => {
                                    onDelete(e)//因为要用e所以外面还要再套一层函数，否则只是把返回值传回去了
                                }}
                                okText="Yes"
                                cancelText="No"
                            >
                                <a href="#">Delete</a>{/*a标签就是出发Popconfirm的按钮*/}
                            </Popconfirm>
                        </Space>
                    </div>
                    :<div style={{textAlign:"right"}}>
                            <strong style={{fontSize:20,float:'left'}}>
                            {e.atitle}</strong>
                    </div>

            }
        >
            {/*rem是一个相对单位*/}
            <Row style={{height: '12rem', marginBottom: '15px'}}>
                <Col span={8} style={{height: '100%'}}>
                    <div style={{textAlign: 'center', height: '100%'}}>
                        <Image
                            height='100%'
                            src={e.avatar}
                        />
                    </div>
                </Col>
                <Col span={16} style={{overflowY:'auto',height:'100%'}}>
                    {e.intro}
                </Col>
            </Row>
            <div>
                <Space>
                    <Button size='small' onClick={() => nav(`/s/artwork/?name=${e.ip}&category=ip`)}>{e.ip}</Button>
                    <Button size='small' onClick={() =>
                        nav(`/s/artwork/?name=&category=${e.kind}`)}
                    >{e.kind}</Button>
                    {/*<Button size='small'>{e.kind}</Button>*/}
                    <Button size='small' onClick={() => nav(`/show/artwork/${e.id}`)}>作品详情</Button>
                    {/*标记点1，这里是这个新增的按钮的有关代码*/}
                    <Button size='small' onClick={()=>nav(`/s/artwork?name=${e.userName}&category=user`)}>
                        上传者：{e.userName}
                    </Button>
                </Space>
            </div>
        </Card>)}
    </div>
}

export default SearchArtworkResult