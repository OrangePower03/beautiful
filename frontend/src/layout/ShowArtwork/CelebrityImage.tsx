import React from "react";
import './scroll.css'
import {useNavigate} from "react-router-dom";

export default function CelebrityImage(props:{
    images:{
        src:string,
        title:string,
        name:string
    }[],
}) {
  const navigate=useNavigate()
  const { images } = props;
  const imageStyle={
      height:168,
      width:117,
  }
  const buttonStyle={
      backgroundColor: 'transparent',
      border:0,
  }
  const buttonClick=(event:any,image:any)=>{
      const celebrityName=image.name
      console.log(celebrityName)

      navigate(`/s/artwork?name=${celebrityName}&category=staff`);
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
                  onClick={(event)=>{
                      buttonClick(event,image)
                  }}
                  title={'点击搜索他的作品'}
              />
              <br/>
              <div style={{textAlign:'center'}}>
                  <button style={buttonStyle} onMouseOver={buttonOver} onMouseOut={buttonOut}
                          onClick={(event)=>{
                              buttonClick(event,image)
                          }}>
                      {image.name}
                  </button>
                  <br/>
                  <p style={{fontSize:12,fontWeight:100}}>{image.title}</p>
              </div>
          </div>
      ))}
    </div>
  );
}

