import React from "react";
import './scroll.css'

export default function CelebrityImage(props:{
    images:{
        src:string,
        title:string,
        name:string
    }[],
}) {
  const { images } = props;
  const imageStyle={
      height:168,
      width:117,
  }
  const buttonStyle={
      backgroundColor: 'transparent',
      border:0,
  }
  const buttonClick=(event:any)=>{
      //todo
      // 点击图像和人名的链接逻辑，写一些搜索人员的sql语句
  }
  const buttonOver=(event:any)=>{
      event.target.style.color='red'
  }
  const buttonOut=(event:any)=>{
      event.target.style.color='black'
  }

  const containStyle={
      display:"flex",
      width:600,
      overflowX: 'auto',
  }
  return (
    <div style={containStyle as any} >
      {images.map((image, index) => (
          <div key={index} style={{marginRight:15}}>
              <img src={image.src} style={imageStyle} alt={"图片加载失败"}
                  onClick={buttonClick}
                  title={'点击搜索他的作品'}
              /> <br/>
              <div style={{textAlign:'center'}}>
                  <button style={buttonStyle} onClick={buttonClick} onMouseOver={buttonOver} onMouseOut={buttonOut}>
                      {image.name}
                  </button><br/>
                  <p style={{fontSize:12,fontWeight:100}}>{image.title}</p>
              </div>
          </div>
      ))}
    </div>
  );
}

