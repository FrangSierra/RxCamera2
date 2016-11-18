package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Surface;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RxCameraCaptureSession {

    @NonNull
    public static Completable abortCaptures(@NonNull final CameraCaptureSession captureSession) {
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
    public static Flowable<RxCameraCaptureEvent> capture(@NonNull final CameraCaptureSession captureSession,
                                                         @NonNull final CaptureRequest captureRequest, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.capture(captureRequest, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @NonNull
    public static Flowable<RxCameraCaptureEvent> captureBurst(@NonNull final CameraCaptureSession captureSession,
                                                              @NonNull final List<CaptureRequest> requests, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.captureBurst(requests, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @NonNull
    public static Completable close(@NonNull final CameraCaptureSession captureSession) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                captureSession.close();
                e.onComplete();
            }
        });
    }

    @NonNull
    public static Observable<CameraDevice> getDevice(@NonNull final CameraCaptureSession captureSession) {
        return Observable.create(new ObservableOnSubscribe<CameraDevice>() {
            @Override
            public void subscribe(ObservableEmitter<CameraDevice> e) throws Exception {
                e.onNext(captureSession.getDevice());
                //TODO Oncomplete/single?
            }
        });
    }

    @NonNull
    public static Observable<Surface> getInputSurface(@NonNull final CameraCaptureSession captureSession) {
        return Observable.create(new ObservableOnSubscribe<Surface>() {
            @Override
            public void subscribe(ObservableEmitter<Surface> e) throws Exception {
                e.onNext(captureSession.getInputSurface());
                //TODO Oncomplete/single?
            }
        });
    }

    @NonNull
    public static Observable<Boolean> isReprocessable(@NonNull final CameraCaptureSession captureSession) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(captureSession.isReprocessable());
                //TODO Oncomplete/single?
            }
        });
    }

    @NonNull
    public static Completable prepare(@NonNull final CameraCaptureSession captureSession, @NonNull final Surface surface) {
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
    public static Flowable<RxCameraCaptureEvent> setRepeatingBurst(@NonNull final CameraCaptureSession captureSession,
                                                                   @NonNull final List<CaptureRequest> requests, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.setRepeatingBurst(requests, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @NonNull
    public static Flowable<RxCameraCaptureEvent> setRepeatingRequest(@NonNull final CameraCaptureSession captureSession,
                                                                     @NonNull final CaptureRequest captureRequest, final Handler handler) {
        return Flowable.create(new FlowableOnSubscribe<RxCameraCaptureEvent>() {
            @Override
            public void subscribe(final FlowableEmitter<RxCameraCaptureEvent> e) throws Exception {
                try {
                    captureSession.setRepeatingRequest(captureRequest, new RxCameraCaptureCallback(e), handler);
                } catch (CameraAccessException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @NonNull
    public static Completable stopRepeating(@NonNull final CameraCaptureSession captureSession) {
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
