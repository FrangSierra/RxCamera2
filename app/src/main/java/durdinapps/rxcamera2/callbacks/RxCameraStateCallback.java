package durdinapps.rxcamera2.callbacks;

import android.hardware.camera2.CameraCaptureSession;
import android.support.annotation.NonNull;

import durdinapps.rxcamera2.RxCameraCaptureSession;
import durdinapps.rxcamera2.wrappers.RxConfigureSessionEvent;
import io.reactivex.SingleEmitter;

public class RxCameraStateCallback extends CameraCaptureSession.StateCallback {
    private final SingleEmitter<RxConfigureSessionEvent> emitter;

    public RxCameraStateCallback(SingleEmitter<RxConfigureSessionEvent> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onConfigured(@NonNull CameraCaptureSession session) {
        emitter.onSuccess(new RxConfigureSessionEvent(new RxCameraCaptureSession(session), RxConfigureSessionEvent.EventType.CONFIGURE));
    }

    @Override
    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
        emitter.onSuccess(new RxConfigureSessionEvent(new RxCameraCaptureSession(session), RxConfigureSessionEvent.EventType.CONFIGURE_FAILED));
    }
}
