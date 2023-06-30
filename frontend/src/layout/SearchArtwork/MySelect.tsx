import { Select } from 'antd';
const { Option } = Select;

function handleChange(value:string) {
    //todo
    if(value==='all'){
        //我全都要
    }
    else if(value==='cartoon'){
        //番剧
    }
    else if(value==='comics'){
        //漫画
    }
    else if(value==='novel'){
        //小说
    }
    else if(value==='staff'){
        //导演？
    }
}

function MySelect() {

  return (
    <Select defaultValue="作品全局搜索" style={{
        width: '8%',
        left: '17%',
        top: '50%',
        position: 'absolute',
        transform: 'translateY(-50%)',
        opacity: 0.8,
    }} onChange={handleChange}>
      <Option value="all">作品全局搜索</Option>
      <Option value="cartoon">番剧</Option>
      <Option value="comics">漫画</Option>
      <Option value="novel">轻小说</Option>
      <Option value="staff">staff</Option>
    </Select>
  );
}

export default MySelect;
