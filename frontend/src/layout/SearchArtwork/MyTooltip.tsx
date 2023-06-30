import React from "react";
import {Tooltip} from 'antd';
function MyTooltip(){

    const tipText ="多选框内，选择你想要检索的内容，默认作品全类型检索，想要查询有关staff请选择staff"
    const btnStyle={
        backgroundColor: 'transparent',
        border: 'none',
        color: "#000000",
        fontSize: 20,
        fontWeight: 900,
    }
    return (
        <div
            style={{
                width: "2%",
                marginRight: "10px",
                position: "relative",
                transform: "translateY(0%)",
            }}
        >
            <Tooltip placement="top" title={tipText} arrow={true}>
                <button style={btnStyle}> &nbsp;?&nbsp; </button>
            </Tooltip>
        </div>
    );

}
export default MyTooltip
