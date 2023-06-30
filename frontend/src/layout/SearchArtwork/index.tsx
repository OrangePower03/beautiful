import {Input, Select, Space} from 'antd';
import {Link, useNavigate} from "react-router-dom";
import React, {useState} from "react";
import MyTooltip from "./MyTooltip";
import MySelect from "./MySelect";
import styles from './styles.module.scss'
import './styles.scss'
import MyHeader from "../MyHeader";

const {Search} = Input;//input返回的是一个哈希表


const SearchArtwork = () => {
    const navigate = useNavigate();//拿返回值出来用，不能直接用useNavigate
    const [category, setCategory] = useState('all')//将其初始的值赋值为'all'
    //useNavigate只返回一个东西

    return <div>
        <MyHeader/>

        <div className={styles.container}>

            <div className={styles.searchBar}>
                <MyTooltip/>

                <Search
                    placeholder="input search text"
                    addonBefore={<Select
                        defaultValue="all"
                        onChange={e => {
                            setCategory(e)
                        }}
                        options={[
                            {label: '作品全局搜索', value: 'all'},
                            {label: '番剧', value: '番剧'},
                            {label: '漫画', value: '漫画'},
                            {label: '轻小说', value: '小说'},
                            {label: '演职人员', value: 'staff'},
                        ]}/>
                    }
                    onSearch={(e) => {
                        if(e==='')
                            alert('请输入您的搜索')
                        else if(e==='_' || e.includes('%'))
                            alert('您觉得有这样的电影吗')
                        else
                            navigate(`/s/artwork?name=${e}&category=${category}`);
                    }} enterButton/>

            </div>
        </div>
    </div>

}

export default SearchArtwork