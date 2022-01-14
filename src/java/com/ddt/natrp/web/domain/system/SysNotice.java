package com.ddt.natrp.web.domain.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class SysNotice {

    /** noticeId **/
    private Long noticeId;

    /** 文章内容 **/
    private String text;

    /** 作者 **/
    private String user;

    /** 文章日期 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /** 日期格式化 */
    public static String formateTime(Date date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
}
