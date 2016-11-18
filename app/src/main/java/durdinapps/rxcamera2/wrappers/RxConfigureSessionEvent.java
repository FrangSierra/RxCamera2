package durdinapps.rxcamera2.wrappers;

import android.support.annotation.NonNull;

import durdinapps.rxcamera2.RxCameraCaptureSession;

public class RxConfigureSessionEvent {
    @NonNull public final RxCameraCaptureSession session;
    @NonNull public final EventType eventType;


    public RxConfigureSessionEvent(RxCameraCaptureSession session, EventType eventType) {
        this.session = session;
        this.eventType = eventType;
    }

    public enum EventType {
        CONFIGURE,
        CONFIGURE_FAILED;
    }
}
