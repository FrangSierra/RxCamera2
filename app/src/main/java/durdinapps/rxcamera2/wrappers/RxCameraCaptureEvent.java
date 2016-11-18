package durdinapps.rxcamera2.wrappers;

import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.support.annotation.NonNull;
import android.view.Surface;

import durdinapps.rxcamera2.RxCameraCaptureSession;


public class RxCameraCaptureEvent {

    @NonNull
    public RxCameraCaptureSession session;
    public CaptureRequest request;
    public long timestamp;
    public long frameNumber;
    public CaptureResult partialResult;
    public TotalCaptureResult totalCaptureResult;
    public CaptureFailure failure;
    public int sequenceId;
    public Surface target;
    @NonNull
    public final EventType eventType;

    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull CaptureRequest request,
                                @NonNull long timestamp, @NonNull long frameNumber) {
        this.session = session;
        this.request = request;
        this.timestamp = timestamp;
        this.frameNumber = frameNumber;
        eventType = EventType.STARTED;
    }


    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull CaptureRequest request,
                                @NonNull CaptureResult partialResult) {
        this.session = session;
        this.request = request;
        this.partialResult = partialResult;
        eventType = EventType.PROGRESSED;
    }

    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull CaptureRequest request,
                                @NonNull TotalCaptureResult totalCaptureResult) {
        this.session = session;
        this.request = request;
        this.totalCaptureResult = totalCaptureResult;
        eventType = EventType.COMPLETED;
    }

    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull CaptureRequest request,
                                @NonNull CaptureFailure failure) {
        this.session = session;
        this.request = request;
        this.failure = failure;
        eventType = EventType.FAILED;
    }

    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull int sequenceId,
                                @NonNull long frameNumber) {
        this.session = session;
        this.sequenceId = sequenceId;
        this.frameNumber = frameNumber;
        eventType = EventType.SEQUENCE_COMPLETED;
    }

    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session,
                                @NonNull int sequenceId) {
        this.session = session;
        this.sequenceId = sequenceId;
        eventType = EventType.SEQUENCE_ABORTED;
    }


    public RxCameraCaptureEvent(@NonNull RxCameraCaptureSession session, @NonNull CaptureRequest request,
                                @NonNull Surface target, @NonNull long frameNumber) {
        this.session = session;
        this.request = request;
        this.target = target;
        this.frameNumber = frameNumber;
        eventType = EventType.BUFFER_LOST;
    }

    public RxCameraCaptureSession getSession() {
        return session;
    }

    public CaptureRequest getRequest() {
        return request;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getFrameNumber() {
        return frameNumber;
    }

    public CaptureResult getPartialResult() {
        return partialResult;
    }

    public TotalCaptureResult getTotalCaptureResult() {
        return totalCaptureResult;
    }

    public CaptureFailure getFailure() {
        return failure;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public Surface getTarget() {
        return target;
    }

    public EventType getEventType() {
        return eventType;
    }

    public enum EventType {
        STARTED,
        PROGRESSED,
        COMPLETED,
        FAILED,
        SEQUENCE_COMPLETED,
        SEQUENCE_ABORTED,
        BUFFER_LOST
    }
}
