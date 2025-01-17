package com.zhongchuang.canting.allive.pusher.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alivc.live.pusher.AlivcLivePushStatsInfo;
import com.zhongchuang.canting.R;

public class PushDiagramStatsFragment extends Fragment {

    public static final String TAG = "PushDiagramStatsFragment";

    private TextView mAudioEncodeText;
    private ProgressBar mAudioEncodeProgress;
    private TextView mAudioPushText;
    private ProgressBar mAudioPushProgress;
    private TextView mVideoCaptureText;
    private ProgressBar mVideoCaptureProgress;
    private TextView mVideoRenderText;
    private ProgressBar mVideoRenderProgress;
    private TextView mVideoEncodeText;
    private ProgressBar mVideoEncodeProgress;
    private TextView mVideoPushText;
    private ProgressBar mVideoPushProgress;
    private TextView mBitAudioEncodeText;
    private ProgressBar mBitAudioEncodeProgress;
    private TextView mBitVideoEncodeText;
    private ProgressBar mBitVideoEncodeProgress;
    private TextView mBitPushText;
    private ProgressBar mBitPushProgress;
    private TextView mVideoRenderBufferText;
    private ProgressBar mVideoRenderBufferProgress;
    private TextView mVideoEncodeBufferText;
    private ProgressBar mVideoEncodeBufferProgress;
    private TextView mVideoUploadBufferText;
    private ProgressBar mVideoUploadBufferProgress;
    private TextView mAudioEncodeBufferText;
    private ProgressBar mAudioEncodeBufferProgress;
    private TextView mAudioUploadBufferText;
    private ProgressBar mAudioUploadBufferProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.push_diagram_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAudioEncodeText = view.findViewById(R.id.audio_encode_fps_text);
        mAudioEncodeProgress = view.findViewById(R.id.audio_encode_fps_bar);
        mAudioPushText = view.findViewById(R.id.audio_push_fps_text);
        mAudioPushProgress = view.findViewById(R.id.audio_push_fps_bar);
        mVideoCaptureText = view.findViewById(R.id.video_capture_fps_text);
        mVideoCaptureProgress = view.findViewById(R.id.video_capture_fps_bar);
        mVideoRenderText = view.findViewById(R.id.video_render_fps_text);
        mVideoRenderProgress = view.findViewById(R.id.video_render_fps_bar);
        mVideoEncodeText = view.findViewById(R.id.video_encode_fps_text);
        mVideoEncodeProgress = view.findViewById(R.id.video_encode_fps_bar);
        mVideoPushText = view.findViewById(R.id.video_push_fps_text);
        mVideoPushProgress = view.findViewById(R.id.video_push_fps_bar);
        mBitAudioEncodeText = view.findViewById(R.id.bit_audio_encode_text);
        mBitAudioEncodeProgress = view.findViewById(R.id.bit_audio_encode_bar);
        mBitVideoEncodeText = view.findViewById(R.id.bit_video_encode_text);
        mBitVideoEncodeProgress = view.findViewById(R.id.bit_video_encode_bar);
        mBitPushText = view.findViewById(R.id.bit_push_text);
        mBitPushProgress = view.findViewById(R.id.bit_push_bar);
        mVideoRenderBufferText = view.findViewById(R.id.video_renderbuffer_text);
        mVideoRenderBufferProgress = view.findViewById(R.id.video_renderbuffer_bar);
        mVideoEncodeBufferText = view.findViewById(R.id.video_encodebuffer_text);
        mVideoEncodeBufferProgress = view.findViewById(R.id.video_encodebuffer_bar);
        mVideoUploadBufferText = view.findViewById(R.id.video_uploadbuffer_text);
        mVideoUploadBufferProgress = view.findViewById(R.id.video_uploadbuffer_bar);
        mAudioEncodeBufferText = view.findViewById(R.id.audio_encodebuffer_text);
        mAudioEncodeBufferProgress = view.findViewById(R.id.audio_encodebuffer_bar);
        mAudioUploadBufferText = view.findViewById(R.id.audio_uploadbuffer_text);
        mAudioUploadBufferProgress = view.findViewById(R.id.audio_uploadbuffer_bar);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateValue(AlivcLivePushStatsInfo alivcLivePushStatsInfo) {

        if(alivcLivePushStatsInfo != null) {
            int audioEncodeFps = alivcLivePushStatsInfo.getAudioEncodeFps();
            int audioPush = alivcLivePushStatsInfo.getAudioUploadFps();
            int videoCaptureFps = alivcLivePushStatsInfo.getVideoCaptureFps();
            int videoRender = alivcLivePushStatsInfo.getVideoRenderFps();
            int videoEncodeFps = alivcLivePushStatsInfo.getVideoEncodeFps();
            int videoPush = alivcLivePushStatsInfo.getVideoUploadeFps();
            int audioBit = alivcLivePushStatsInfo.getAudioEncodeBitrate();
            int videoBit = alivcLivePushStatsInfo.getVideoEncodeBitrate();
            int pushBit = alivcLivePushStatsInfo.getVideoUploadBitrate();
            int videoRenderBuffer = alivcLivePushStatsInfo.getVideoFramesInRenderBuffer();
            int videoEncodeBuffer = alivcLivePushStatsInfo.getVideoFramesInEncodeBuffer();
            int videoUploadBuffer = alivcLivePushStatsInfo.getVideoPacketsInUploadBuffer();
            int audioEncodeBuffer = alivcLivePushStatsInfo.getAudioFrameInEncodeBuffer();
            int audioUploadBuffer = alivcLivePushStatsInfo.getAudioPacketsInUploadBuffer();
            mAudioEncodeText.setText(String.valueOf(audioEncodeFps));
            mAudioEncodeProgress.setProgress(audioEncodeFps);
            mAudioPushText.setText(String.valueOf(audioPush));
            mAudioPushProgress.setProgress(audioPush);
            mVideoCaptureText.setText(String.valueOf(videoCaptureFps));
            mVideoCaptureProgress.setProgress(videoCaptureFps);
            mVideoRenderText.setText(String.valueOf(videoRender));
            mVideoRenderProgress.setProgress(videoRender);
            mVideoEncodeText.setText(String.valueOf(videoEncodeFps));
            mVideoEncodeProgress.setProgress(videoEncodeFps);
            mVideoPushText.setText(String.valueOf(videoPush));
            mVideoPushProgress.setProgress(videoPush);
            mBitAudioEncodeText.setText(String.valueOf(audioBit));
            mBitAudioEncodeProgress.setProgress(audioBit);
            mBitVideoEncodeText.setText(String.valueOf(videoBit));
            mBitVideoEncodeProgress.setProgress(videoBit);
            mBitPushText.setText(String.valueOf(pushBit));
            mBitPushProgress.setProgress(pushBit);
            mVideoRenderBufferText.setText(String.valueOf(videoRenderBuffer));
            mVideoRenderBufferProgress.setProgress(videoRenderBuffer);
            mVideoEncodeBufferText.setText(String.valueOf(videoEncodeBuffer));
            mVideoEncodeBufferProgress.setProgress(videoEncodeBuffer);
            mVideoUploadBufferText.setText(String.valueOf(videoUploadBuffer));
            mVideoUploadBufferProgress.setProgress(videoUploadBuffer);
            mAudioEncodeBufferText.setText(String.valueOf(audioEncodeBuffer));
            mAudioEncodeBufferProgress.setProgress(audioEncodeBuffer);
            mAudioUploadBufferText.setText(String.valueOf(audioUploadBuffer));
            mAudioUploadBufferProgress.setProgress(audioUploadBuffer);

        }

    }
}
