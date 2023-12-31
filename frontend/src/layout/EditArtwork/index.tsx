import {AutoComplete, Button, Checkbox, Col, DatePicker, Form, Input, message, Row, Select, Space} from 'antd';
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";

const {Search} = Input;
import styles from './styles.module.scss'
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons";
import axios from "axios";
import MyHeader from "../MyHeader";
import dayjs, {Dayjs} from "dayjs";


interface KindDto {
    kid: number
    name: string
}

interface TitleDto {
    tid: number
    name: string
}

interface ArtworkEditDto {

    time:(number | Dayjs)
    aid: number
    intro: string
    kind: number
    title: number
    ip: number
    avatar: string
    resourceAddress: string
    celebritys: {
        // actid: number
        name: string
        title: number
        avatar: string
    }[]
}

const AddArtwork = () => {
    const navigate = useNavigate();
    const [artworkEditDto, setArtworkEditDto] = useState<ArtworkEditDto>()
    const [kindList, setKindList] = useState<KindDto[]>([])
    const [titleList, setTitleList] = useState<TitleDto[]>([])
    const [celebrityList, setCelebrityList] = useState<string[]>([])
    const {aid} = useParams()
    const [ipList, setipList] = useState<string[]>([])
    const config={
        headers:{
            token: localStorage.getItem("token")
        }
    }
    useEffect(() => {
        axios.get<ArtworkEditDto>(`/artwork/edit/${aid}`,config).then(e => {
            setArtworkEditDto({...e.data,time:dayjs.unix(e.data.time as number)})
        })
    }, [])

    useEffect(() => {
        axios.get<KindDto[]>('/kind',config).then(e => {
            setKindList(e.data)
        })
    }, [])

    useEffect(() => {
        axios.get<TitleDto[]>('/title',config).then(e => {
            setTitleList(e.data)
        })
    }, [])

    useEffect(() => {
        axios.get<string[]>('/celebrity',config).then(e => {
            setCelebrityList(e.data)
        })
    }, [])

    useEffect(() => {
        axios.get<string[]>('/ip',config).then(e => {
            setipList(e.data)
        })
    }, [])

    return <div>
        <MyHeader/>
        <div className={styles.container}>
            {artworkEditDto &&
                <Form
                    name="basic"
                    labelCol={{span: 4}}
                    // wrapperCol={{ span: 16 }}
                    initialValues={artworkEditDto}
                    onFinish={(e) => {
                        const data = {
                            ...e,
                            actid: 0,
                            aid:artworkEditDto?.aid,
                            time: e.time.unix(),
                        } as ArtworkEditDto
                        console.log(data)
                        axios.put('/artwork',data,config).then(()=>{
                            message.success("修改成功！")
                            navigate(-1)
                        }).catch(e=>{
                            message.error(e.response.data.title+
                                '\n'+e.response.data.detail)
                        })
                    }}
                    className={styles.form}

                    onFinishFailed={(e)=>{
                            const data={...e.values }as ArtworkEditDto
                            console.log(data)
                        }
                    }
                    autoComplete="off"
                >
                    <Form.Item
                        label="作品名称"
                        name="title"
                        rules={[{required: true, message: 'Please input your username!'}]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="图像地址"
                        name="avatar"
                        rules={[{required: true, message: 'Please input your url !!'}]}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item
                        label="资源地址"
                        name="resourceAddress"
                        rules={[{required: true,message: 'Please input your url !!'}]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="发布日期"
                        name="time"
                        rules={[{required: true}]}
                    >
                        <DatePicker/>
                    </Form.Item>

                    <Form.Item
                        label="系列"
                        name="ip"
                        rules={[{required: true}]}
                    >
                        <AutoComplete
                            style={{width: 200}}
                            placeholder="系列作品"
                            dataSource={ipList.map(e => ({
                                value: e, text: e
                            }))}
                        />
                    </Form.Item>
                    <Form.Item
                        label="介绍"
                        name="intro"
                        rules={[{required: true}]}
                    >
                        <Input.TextArea rows={10}/>
                    </Form.Item>

                    <Form.Item
                        label="类型"
                        name="kind"
                        rules={[{required: true}]}
                    >
                        <Select options={kindList.map((e) => {
                            //console.log(e)
                            return ({
                                label: e.name, value: e.kid
                            })
                        })}/>
                        {/*    map函数就是把数组中每一个元素都取出对应的值，新数组长度与老数组相同   */}
                    </Form.Item>

                    <Form.List name="celebritys">
                        {(fields, {add, remove}) => (
                            <>
                                {fields.map(({key, name: index, ...restField}) => (
                                    <Row key={key} style={{width: '100%'}}>
                                        <Col span={4} style={{textAlign: 'right', lineHeight: '2'}}>
                                            {index ? <div></div> : <label className={styles.mylabel}>演职人员</label>}
                                        </Col>
                                        <Col span={16}>
                                            <Row gutter={[16, 16]}>
                                                <Col span={8}>
                                                    {/*<Form.Item*/}
                                                    {/*    {...restField}*/}
                                                    {/*    name={[index, 'actid']}*/}
                                                    {/*    initialValue={(() => {*/}
                                                    {/*        const c = artworkEditDto?.celebritys[key]*/}
                                                    {/*        return c !== undefined ? c.actid : undefined;*/}
                                                    {/*    })()}*/}
                                                    {/*    style={{display:'none'}}*/}
                                                    {/*    rules={[{required: true}]}*/}
                                                    {/*>*/}

                                                    {/*</Form.Item>*/}
                                                    <Form.Item
                                                        {...restField}
                                                        name={[index, 'name']}
                                                        rules={[{required: true, message: 'Missing first name'}]}
                                                    >
                                                        <AutoComplete dataSource={celebrityList.map(e => ({
                                                            value: e, text: e
                                                        }))} placeholder="贡献者"/>
                                                    </Form.Item>
                                                </Col>
                                                <Col span={4}>
                                                    <Form.Item
                                                        {...restField}
                                                        name={[index, 'title']}
                                                        rules={[{required: true, message: 'Missing last name'}]}
                                                    >
                                                        <Select options={titleList.map(e => ({
                                                            label: e.name, value: e.tid
                                                        }))} placeholder="职位"/>
                                                    </Form.Item>
                                                </Col>

                                                <Col span={10}>
                                                    <Form.Item
                                                        {...restField}
                                                        name={[index, 'avatar']}
                                                        rules={[{required: true, message: 'Missing avatar'}]}
                                                    >
                                                        <Input placeholder="头像url"/>
                                                    </Form.Item>
                                                </Col>

                                                <Col span={2}>
                                                    <Button type="ghost" onClick={() => remove(index)}
                                                            icon={<MinusCircleOutlined/>}
                                                    />
                                                </Col>
                                            </Row>

                                        </Col>

                                    </Row>
                                ))}
                                <Row key={-1} style={{width: '100%'}}>
                                    <Col span={4} style={{textAlign: 'right', lineHeight: '2'}}>
                                    </Col>
                                    <Col span={20}>
                                        <Form.Item>
                                            <Button type="dashed" onClick={() => add()} block
                                                    icon={<PlusOutlined/>}
                                            >
                                                新增演职人员
                                            </Button>
                                        </Form.Item>
                                    </Col>
                                </Row>
                            </>
                        )}
                    </Form.List>
                    <Form.Item
                        // label=' '
                        wrapperCol={{offset: 8, span: 16}}
                    >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>

                </Form>}
        </div>
    </div>
}

export default AddArtwork