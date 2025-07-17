package cn.unipus.modeladapter.remote.starter.http.ipublish.dto;

import lombok.Data;

/**
 * 音频设置
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
public class AudioSetting {

    /**
     * 播放方式 点击播放 自动播放
     */
    private String playType;

    /**
     * 作答前显示，作答后显示
     */
    private String subtitle;

    /**
     * 是否设置播放次数
     */
    private Boolean setPlayNum;

    /**
     * 设置播放次数是true的时候，填写播放多少次
     */
    private Integer playNum;
}