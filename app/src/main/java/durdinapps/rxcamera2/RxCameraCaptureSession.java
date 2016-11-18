package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Surface;

import java.util.List;

import durdinapps.rxcamera2.callbacks.RxCameraCaptureCallback;
import durdinapps.rxcamera2.wrappers.RxCameraCaptureEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class RxCameraCaptureSession {

    private final CameraCaptureSession captureSession;

    public RxCameraCaptureSession(CameraCaptureSession session){
        this.captureSession = session;
    }

    @NonNull
    public CameraCaptureSession getCaptureSession() {
        return captureSession;
    }

    @NonNull
    public Completable abortCaptures() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    captureSession.abortCaptures();
                    e.onComplete();
                } catch (CameraAccessException | IllegalStateException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Flowable<RxCameraCaptureEvent> capture(@NonNull final CaptureRequest captureRequest, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.capture(captureRequest, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.DROP);
    }

    @NonNull
    public Flowable<RxCameraCaptureEvent> captureBurst(@NonNull final List<CaptureRequest> requests, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.captureBurst(requests, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.DROP);
    }

    @NonNull
    public Completable close() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                captureSession.close();
                e.onComplete();
            }
        });
    }

    @NonNull
    public Completable prepare(@NonNull final Surface surface) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    captureSession.prepare(surface);
                    e.onComplete();
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public Flowable<RxCameraCaptureEvent> setRepeatingBurst(@NonNull final List<CaptureRequest> requests, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.setRepeatingBurst(requests, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.DROP);
    }

    @NonNull
    public Flowable<RxCameraCaptureEvent> setRepeatingRequest(@NonNull final CaptureRequest captureRequest, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.setRepeatingRequest(captureRequest, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.DROP);
    }

    @NonNull
    public Completable stopRepeating() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    captureSession.stopRepeating();
                    e.onComplete();
                } catch (CameraAccessException | IllegalStateException ex) {
                    e.onError(ex);
                }
            }
        });
    }
}
