package com.google.android.youtube.player.internal;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.C1107e;
import com.google.android.youtube.player.internal.C1110f;
import com.google.android.youtube.player.internal.C1113g;
import com.google.android.youtube.player.internal.C1116h;
import java.util.List;

/* renamed from: com.google.android.youtube.player.internal.s */
public final class C1146s implements YouTubePlayer {

    /* renamed from: a */
    private C1100b f247a;

    /* renamed from: b */
    private C1104d f248b;

    public C1146s(C1100b bVar, C1104d dVar) {
        this.f247a = (C1100b) C1098ab.m124a(bVar, (Object) "connectionClient cannot be null");
        this.f248b = (C1104d) C1098ab.m124a(dVar, (Object) "embeddedPlayer cannot be null");
    }

    /* renamed from: a */
    public final View mo16679a() {
        try {
            return (View) C1157v.m368a(this.f248b.mo16603s());
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: a */
    public final void mo16680a(Configuration configuration) {
        try {
            this.f248b.mo16563a(configuration);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: a */
    public final void mo16681a(boolean z) {
        try {
            this.f248b.mo16572a(z);
            this.f247a.mo16556a(z);
            this.f247a.mo16662d();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: a */
    public final boolean mo16682a(int i, KeyEvent keyEvent) {
        try {
            return this.f248b.mo16573a(i, keyEvent);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: a */
    public final boolean mo16683a(Bundle bundle) {
        try {
            return this.f248b.mo16574a(bundle);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void addFullscreenControlFlag(int i) {
        try {
            this.f248b.mo16585d(i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: b */
    public final void mo16684b() {
        try {
            this.f248b.mo16597m();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: b */
    public final void mo16685b(boolean z) {
        try {
            this.f248b.mo16588e(z);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: b */
    public final boolean mo16686b(int i, KeyEvent keyEvent) {
        try {
            return this.f248b.mo16581b(i, keyEvent);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: c */
    public final void mo16687c() {
        try {
            this.f248b.mo16598n();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void cuePlaylist(String str) {
        cuePlaylist(str, 0, 0);
    }

    public final void cuePlaylist(String str, int i, int i2) {
        try {
            this.f248b.mo16570a(str, i, i2);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void cueVideo(String str) {
        cueVideo(str, 0);
    }

    public final void cueVideo(String str, int i) {
        try {
            this.f248b.mo16569a(str, i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void cueVideos(List<String> list) {
        cueVideos(list, 0, 0);
    }

    public final void cueVideos(List<String> list, int i, int i2) {
        try {
            this.f248b.mo16571a(list, i, i2);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: d */
    public final void mo16688d() {
        try {
            this.f248b.mo16599o();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: e */
    public final void mo16689e() {
        try {
            this.f248b.mo16600p();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: f */
    public final void mo16690f() {
        try {
            this.f248b.mo16601q();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: g */
    public final void mo16691g() {
        try {
            this.f248b.mo16596l();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final int getCurrentTimeMillis() {
        try {
            return this.f248b.mo16592h();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final int getDurationMillis() {
        try {
            return this.f248b.mo16593i();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final int getFullscreenControlFlags() {
        try {
            return this.f248b.mo16594j();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    /* renamed from: h */
    public final Bundle mo16692h() {
        try {
            return this.f248b.mo16602r();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final boolean hasNext() {
        try {
            return this.f248b.mo16587d();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final boolean hasPrevious() {
        try {
            return this.f248b.mo16589e();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final boolean isPlaying() {
        try {
            return this.f248b.mo16584c();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void loadPlaylist(String str) {
        loadPlaylist(str, 0, 0);
    }

    public final void loadPlaylist(String str, int i, int i2) {
        try {
            this.f248b.mo16578b(str, i, i2);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void loadVideo(String str) {
        loadVideo(str, 0);
    }

    public final void loadVideo(String str, int i) {
        try {
            this.f248b.mo16577b(str, i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void loadVideos(List<String> list) {
        loadVideos(list, 0, 0);
    }

    public final void loadVideos(List<String> list, int i, int i2) {
        try {
            this.f248b.mo16579b(list, i, i2);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void next() {
        try {
            this.f248b.mo16590f();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void pause() {
        try {
            this.f248b.mo16575b();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void play() {
        try {
            this.f248b.mo16561a();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void previous() {
        try {
            this.f248b.mo16591g();
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void release() {
        mo16681a(true);
    }

    public final void seekRelativeMillis(int i) {
        try {
            this.f248b.mo16576b(i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void seekToMillis(int i) {
        try {
            this.f248b.mo16562a(i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setFullscreen(boolean z) {
        try {
            this.f248b.mo16580b(z);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setFullscreenControlFlags(int i) {
        try {
            this.f248b.mo16582c(i);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setManageAudioFocus(boolean z) {
        try {
            this.f248b.mo16586d(z);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setOnFullscreenListener(final YouTubePlayer.OnFullscreenListener onFullscreenListener) {
        try {
            this.f248b.mo16564a((C1107e) new C1107e.C1108a() {
                /* renamed from: a */
                public final void mo16606a(boolean z) {
                    onFullscreenListener.onFullscreen(z);
                }
            });
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setPlaybackEventListener(final YouTubePlayer.PlaybackEventListener playbackEventListener) {
        try {
            this.f248b.mo16565a((C1110f) new C1110f.C1111a() {
                /* renamed from: a */
                public final void mo16610a() {
                    playbackEventListener.onPlaying();
                }

                /* renamed from: a */
                public final void mo16611a(int i) {
                    playbackEventListener.onSeekTo(i);
                }

                /* renamed from: a */
                public final void mo16612a(boolean z) {
                    playbackEventListener.onBuffering(z);
                }

                /* renamed from: b */
                public final void mo16613b() {
                    playbackEventListener.onPaused();
                }

                /* renamed from: c */
                public final void mo16614c() {
                    playbackEventListener.onStopped();
                }
            });
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setPlayerStateChangeListener(final YouTubePlayer.PlayerStateChangeListener playerStateChangeListener) {
        try {
            this.f248b.mo16566a((C1113g) new C1113g.C1114a() {
                /* renamed from: a */
                public final void mo16618a() {
                    playerStateChangeListener.onLoading();
                }

                /* renamed from: a */
                public final void mo16619a(String str) {
                    playerStateChangeListener.onLoaded(str);
                }

                /* renamed from: b */
                public final void mo16620b() {
                    playerStateChangeListener.onAdStarted();
                }

                /* renamed from: b */
                public final void mo16621b(String str) {
                    YouTubePlayer.ErrorReason errorReason;
                    try {
                        errorReason = YouTubePlayer.ErrorReason.valueOf(str);
                    } catch (IllegalArgumentException | NullPointerException e) {
                        errorReason = YouTubePlayer.ErrorReason.UNKNOWN;
                    }
                    playerStateChangeListener.onError(errorReason);
                }

                /* renamed from: c */
                public final void mo16622c() {
                    playerStateChangeListener.onVideoStarted();
                }

                /* renamed from: d */
                public final void mo16623d() {
                    playerStateChangeListener.onVideoEnded();
                }
            });
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setPlayerStyle(YouTubePlayer.PlayerStyle playerStyle) {
        try {
            this.f248b.mo16568a(playerStyle.name());
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setPlaylistEventListener(final YouTubePlayer.PlaylistEventListener playlistEventListener) {
        try {
            this.f248b.mo16567a((C1116h) new C1116h.C1117a() {
                /* renamed from: a */
                public final void mo16627a() {
                    playlistEventListener.onPrevious();
                }

                /* renamed from: b */
                public final void mo16628b() {
                    playlistEventListener.onNext();
                }

                /* renamed from: c */
                public final void mo16629c() {
                    playlistEventListener.onPlaylistEnded();
                }
            });
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }

    public final void setShowFullscreenButton(boolean z) {
        try {
            this.f248b.mo16583c(z);
        } catch (RemoteException e) {
            throw new C1138q(e);
        }
    }
}
