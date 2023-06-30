import React, { useState } from 'react';
import './scroll.css'

function ChangeRecord(props:{
    changeRecord:{date:string,User:string}[]
}) {
  const {changeRecord=[{date:'',User:''},]} = props;
  const maxRecord= 5    //最大历史记录，可调
  const [showFull, setShowFull] = useState(false);
  const recordStyle={
      width:300,
      maxHeight:150,
      overflowY: 'auto',
  }

  if(changeRecord.length <= maxRecord) {
      console.log('111')
     return (
         <div style={recordStyle as any}>
              {changeRecord.map((change, index) => (
                 <div key={index} style={{marginBottom:5}}>
                    <p>
                        {change.date}&nbsp;&nbsp;&nbsp;&nbsp;{change.User}
                    </p>
                 </div>
              ))}
         </div>
     )
  }

  const truncatedRecord=changeRecord.slice(0,5)
  const buttonStyle={
    backgroundColor:"transparent",
    border:0,
    color:'rgb(121,225,239)'
  }
  const buttonOver=(event:any)=>{
    event.target.style.color='rgb(0, 186, 233)'
  }
  const buttonOut=(event:any)=>{
     event.target.style.color='rgb(121,225,239)'
  }

  function handleShowMore() {
    setShowFull(true);
  }
  function handleShowLess(){
      setShowFull(false);
  }

  return (
      <div style={recordStyle as any}>
          {showFull ? (
              <>
                  <div>
                      {changeRecord.map((change, index) => (
                         <div key={index} style={{marginBottom:5}}>
                            <p>
                                {change.date}&nbsp;&nbsp;&nbsp;&nbsp;{change.User}
                            </p>
                         </div>
                      ))}
                  </div>
                  <button style={buttonStyle}
                      onMouseOver={buttonOver}
                      onClick={handleShowLess}
                      onMouseOut={buttonOut}
                  >收起</button>
              </>
          )
              :
          (
              <>
                  <div>
                      {truncatedRecord.map((change, index) => (
                         <div key={index} style={{marginBottom:5}}>
                            <p>
                                {change.date}&nbsp;&nbsp;&nbsp;&nbsp;{change.User}
                            </p>
                         </div>
                      ))}
                  </div>
                  <button style={buttonStyle}
                      onMouseOver={buttonOver}
                      onClick={handleShowMore}
                      onMouseOut={buttonOut}
                  >更多</button>
              </>
          )}
      </div>
  );
}

export default ChangeRecord;