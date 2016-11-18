package durdinapps.rxcamera2.callbacks;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;

import durdinapps.rxcamera2.RxCameraCaptureSession;
import durdinapps.rxcamera2.wrappers.RxCameraCaptureEvent;
import io.reactivex.Emitter;

public class RxCameraCaptureCallback extends CameraCaptureSession.CaptureCallback {

    private final Emitter<RxCameraCaptureEvent> emitter;

    public RxCameraCaptureCallback(Emitter<RxCameraCaptureEvent> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
        super.onCaptureStarted(session, request, timestamp, frameNumber);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), request, timestamp, frameNumber));
    }

    @Override
    public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
        super.onCaptureProgressed(session, request, partialResult);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), request, partialResult));
    }

    @Override
    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
        super.onCaptureCompleted(session, request, result);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), request, result));
        emitter.onComplete();
    }

    @Override
    public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
        super.onCaptureFailed(session, request, failure);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), request, failure));
        emitter.onComplete();
    }

    @Override
    public void onCaptureSequenceCompleted(CameraCaptureSession session, int sequenceId, long frameNumber) {
        super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), sequenceId, frameNumber));
    }

    @Override
    public void onCaptureSequenceAborted(CameraCaptureSession session, int sequenceId) {
        super.onCaptureSequenceAborted(session, sequenceId);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), sequenceId));
    }

    @Override
    public void onCaptureBufferLost(CameraCaptureSession session, CaptureRequest request, Surface target, long frameNumber) {
        super.onCaptureBufferLost(session, request, target, frameNumber);
        emitter.onNext(new RxCameraCaptureEvent(new RxCameraCaptureSession(session), request, target, frameNumber));
    }
}
