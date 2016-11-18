package durdinapps.rxcamera2;

import android.hardware.camera2.CameraCaptureSession;

import io.reactivex.SingleEmitter;

public class RxCameraStateCallback extends CameraCaptureSession.StateCallback {
    private final SingleEmitter<RxConfigureSessionEvent> emitter;

    public RxCameraStateCallback(SingleEmitter<RxConfigureSessionEvent> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onConfigured(CameraCaptureSession session) {
        emitter.onSuccess(new RxConfigureSessionEvent(session, RxConfigureSessionEvent.EventType.CONFIGURE));
    }

    @Override
    public void onConfigureFailed(CameraCaptureSession session) {
        emitter.onSuccess(new RxConfigureSessionEvent(session, RxConfigureSessionEvent.EventType.CONFIGURE_FAILED));
    }
}
