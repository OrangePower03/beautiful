import React, {useState} from 'react';
import './scroll.css'

function Intro(props:{
    text:string
}) {
    const {text = ''} = props;
    const maxChar = 100     //最大简介字数，可调
    const [showFull, setShowFull] = useState(false);
    const introStyle = {
        width: '100%',
        height: 150,
        // position: 'absolute',
        wordWrap: 'break-word', /* 自动换行 */
        wordBreak: 'break-all', /* 单词内断行 */
        overflowY: 'auto', /* 设置自动出现滚动条 */
    }


    if (text.length < maxChar) {
        return (
            <div style={introStyle as any} >
                {text.split('\n').map((line, i) =>
                    <div key={i}>{line}</div>)}
            </div>
        )
    }
    const truncatedText = text.substring(0, maxChar);
    const buttonStyle = {
        backgroundColor: "transparent",
        border: 0,
        color: 'rgb(121,225,239)'
    }
    const buttonOver = (event: any) => {
        event.target.style.color = 'rgb(0, 186, 233)'
    }
    const buttonOut = (event: any) => {
        event.target.style.color = 'rgb(121,225,239)'
    }

    function handleShowMore() {
        setShowFull(true);
    }

    return (
        <div style={introStyle as any}>
            {showFull ? (<p>{text.split('\n').map((line, i) =>
                    <div key={i}>{line}</div>)}</p>) :
                (
                    <>
                        <p>{truncatedText}...</p>
                        <button style={buttonStyle}
                                onMouseOver={buttonOver}
                                onClick={handleShowMore}
                                onMouseOut={buttonOut}
                        >更多
                        </button>
                    </>
                )}
        </div>
    );
}

export default Intro;
