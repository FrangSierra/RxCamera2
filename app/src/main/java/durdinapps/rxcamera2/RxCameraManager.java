package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.annotation.NonNull;

import durdinapps.rxcamera2.wrappers.OpenCameraEvent;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static durdinapps.rxcamera2.wrappers.OpenCameraEvent.EventType.DISCONNECTED;
import static durdinapps.rxcamera2.wrappers.OpenCameraEvent.EventType.ERROR;
import static durdinapps.rxcamera2.wrappers.OpenCameraEvent.EventType.OPENED;

public class RxCameraManager {

    private final CameraManager cameraManager;

    public RxCameraManager(CameraManager cameraManager){
        this.cameraManager = cameraManager;
    }

    @NonNull
    public CameraManager getCameraManager(){
        return cameraManager;
    }

    @NonNull
    public Observable<OpenCameraEvent> openCamera(@NonNull final String cameraId,
                                                  @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe<OpenCameraEvent>() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                try {
                    cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice camera) {
                            e.onNext(new OpenCameraEvent(new RxCameraDevice(camera), OPENED));
                        }

                        @Override
                        public void onDisconnected(CameraDevice camera) {
                            e.onNext(new OpenCameraEvent(new RxCameraDevice(camera), DISCONNECTED));
                        }

                        @Override
                        public void onError(CameraDevice camera, int error) {
                            e.onNext(new OpenCameraEvent(new RxCameraDevice(camera), ERROR));
                        }
                    }, handler);
                } catch (CameraAccessException | IllegalArgumentException | SecurityException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Observable<Boolean> registerAvailabilityCallback(@NonNull final CameraManager cameraManager,
                                                                   @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                final CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() {
                    @Override
                    public void onCameraAvailable(String cameraId) {
                        e.onNext(true);
                    }

                    @Override
                    public void onCameraUnavailable(String cameraId) {
                        e.onNext(false);
                    }
                };

                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        cameraManager.unregisterAvailabilityCallback(availabilityCallback);
                    }
                });

                try {
                    cameraManager.registerAvailabilityCallback(availabilityCallback, handler);
                } catch (IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Observable<Boolean> registerTorchCallback(@NonNull final CameraManager cameraManager,
                                                            @NonNull final Handler handler) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                final CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
                    @Override
                    public void onTorchModeUnavailable(String cameraId) {
                        e.onNext(false);
                    }

                    @Override
                    public void onTorchModeChanged(String cameraId, boolean enabled) {
                        e.onNext(enabled);
                    }
                };

                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        cameraManager.unregisterTorchCallback(torchCallback);
                    }
                });
                try {
                    cameraManager.registerTorchCallback(torchCallback, handler);
                } catch (IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    public static Completable setTorchMode(@NonNull final CameraManager cameraManager,
                                           @NonNull final String cameraId, final boolean enabled) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    cameraManager.setTorchMode(cameraId, enabled);
                    e.onComplete();
                } catch (CameraAccessException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }
}
