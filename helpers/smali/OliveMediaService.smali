.class public Lolive/app/OliveMediaService;
.super Landroid/app/Service;
.source "OliveMediaService.java"


# static fields
.field private static final ACTION_NEXT:Ljava/lang/String; = "olive.app.NEXT"

.field private static final ACTION_PREVIOUS:Ljava/lang/String; = "olive.app.PREVIOUS"

.field private static final ACTION_TOGGLE:Ljava/lang/String; = "olive.app.TOGGLE"

.field private static final CHANNEL:Ljava/lang/String; = "olive_playback"

.field private static final IDLE_STOP_MS:J = 0x927c0L

.field private static final NOTIFICATION_ID:I = 0x1

.field private static final TOGGLE:Ljava/lang/String; = "controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0"

.field static volatile appVisible:Z


# instance fields
.field private album:Ljava/lang/String;

.field private art:Landroid/graphics/Bitmap;

.field private artUrl:Ljava/lang/String;

.field private artist:Ljava/lang/String;

.field private callback:Landroid/media/session/MediaSession$Callback;

.field private devIP:Ljava/lang/String;

.field private durationMs:J

.field private final io:Ljava/util/concurrent/ExecutorService;

.field private lastMetadata:Landroid/media/MediaMetadata;

.field private lastState:Landroid/media/session/PlaybackState;

.field private final main:Landroid/os/Handler;

.field private objId:Ljava/lang/String;

.field private final poll:Ljava/lang/Runnable;

.field private positionMs:J

.field private screenOn:Landroid/content/BroadcastReceiver;

.field private session:Landroid/media/session/MediaSession;

.field private stoppedSince:J

.field private title:Ljava/lang/String;

.field private transport:Ljava/lang/String;

.field private volume:Landroid/media/VolumeProvider;

.field private volumePercent:I


# direct methods
.method public constructor <init>()V
    .locals 2

    .prologue
    .line 40
    invoke-direct {p0}, Landroid/app/Service;-><init>()V

    .line 51
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->main:Landroid/os/Handler;

    .line 52
    invoke-static {}, Ljava/util/concurrent/Executors;->newSingleThreadExecutor()Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveMediaService;->io:Ljava/util/concurrent/ExecutorService;

    .line 63
    const-string v0, "STOPPED"

    iput-object v0, p0, Lolive/app/OliveMediaService;->transport:Ljava/lang/String;

    .line 64
    const-string v0, ""

    iput-object v0, p0, Lolive/app/OliveMediaService;->objId:Ljava/lang/String;

    .line 65
    const-string v0, ""

    iput-object v0, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    .line 66
    const-string v0, ""

    iput-object v0, p0, Lolive/app/OliveMediaService;->artist:Ljava/lang/String;

    .line 67
    const-string v0, ""

    iput-object v0, p0, Lolive/app/OliveMediaService;->album:Ljava/lang/String;

    .line 68
    const-string v0, ""

    iput-object v0, p0, Lolive/app/OliveMediaService;->artUrl:Ljava/lang/String;

    .line 72
    const/4 v0, -0x1

    iput v0, p0, Lolive/app/OliveMediaService;->volumePercent:I

    .line 75
    new-instance v0, Lolive/app/OliveMediaService$1;

    invoke-direct {v0, p0}, Lolive/app/OliveMediaService$1;-><init>(Lolive/app/OliveMediaService;)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->poll:Ljava/lang/Runnable;

    return-void
.end method

.method static synthetic access$000(Lolive/app/OliveMediaService;)V
    .locals 0

    .prologue
    .line 40
    invoke-direct {p0}, Lolive/app/OliveMediaService;->fetch()V

    return-void
.end method

.method static synthetic access$100(Lolive/app/OliveMediaService;)Z
    .locals 1

    .prologue
    .line 40
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isStopped()Z

    move-result v0

    return v0
.end method

.method static synthetic access$1000(Lolive/app/OliveMediaService;Z)V
    .locals 0

    .prologue
    .line 40
    invoke-direct {p0, p1}, Lolive/app/OliveMediaService;->scheduleNext(Z)V

    return-void
.end method

.method static synthetic access$1100(Lolive/app/OliveMediaService;)Landroid/os/Handler;
    .locals 1

    .prologue
    .line 40
    iget-object v0, p0, Lolive/app/OliveMediaService;->main:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$1200(Lolive/app/OliveMediaService;I)V
    .locals 0

    .prologue
    .line 40
    invoke-direct {p0, p1}, Lolive/app/OliveMediaService;->setOliveVolume(I)V

    return-void
.end method

.method static synthetic access$200(Lolive/app/OliveMediaService;)Ljava/lang/String;
    .locals 1

    .prologue
    .line 40
    iget-object v0, p0, Lolive/app/OliveMediaService;->objId:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lolive/app/OliveMediaService;)Z
    .locals 1

    .prologue
    .line 40
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPaused()Z

    move-result v0

    return v0
.end method

.method static synthetic access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .prologue
    .line 40
    invoke-direct {p0, p1, p2}, Lolive/app/OliveMediaService;->command(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$500(Lolive/app/OliveMediaService;)Z
    .locals 1

    .prologue
    .line 40
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v0

    return v0
.end method

.method static synthetic access$600(Lolive/app/OliveMediaService;J)V
    .locals 1

    .prologue
    .line 40
    invoke-direct {p0, p1, p2}, Lolive/app/OliveMediaService;->pollSoon(J)V

    return-void
.end method

.method static synthetic access$700(Lolive/app/OliveMediaService;)Ljava/lang/String;
    .locals 1

    .prologue
    .line 40
    iget-object v0, p0, Lolive/app/OliveMediaService;->artUrl:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$800(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 1

    .prologue
    .line 40
    invoke-static {p0}, Lolive/app/OliveMediaService;->loadArt(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$900(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V
    .locals 0

    .prologue
    .line 40
    invoke-direct {p0, p1, p2, p3}, Lolive/app/OliveMediaService;->apply(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V

    return-void
.end method

.method private apply(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V
    .locals 2

    .prologue
    .line 253
    const-string v0, "transportState"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveMediaService;->transport:Ljava/lang/String;

    .line 254
    const-string v0, "objId"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveMediaService;->objId:Ljava/lang/String;

    .line 255
    const-string v0, "INTERNET_RADIO"

    const-string v1, "PlayType"

    invoke-static {p1, v1}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "stationTitle"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-lez v0, :cond_1

    const/4 v0, 0x1

    .line 256
    :goto_0
    const-string v1, "dctitle"

    invoke-static {p1, v1}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    .line 257
    if-eqz v0, :cond_2

    const-string v1, "stationTitle"

    invoke-static {p1, v1}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    :goto_1
    iput-object v1, p0, Lolive/app/OliveMediaService;->artist:Ljava/lang/String;

    .line 258
    if-eqz v0, :cond_3

    const-string v1, ""

    :goto_2
    iput-object v1, p0, Lolive/app/OliveMediaService;->album:Ljava/lang/String;

    .line 259
    if-eqz v0, :cond_4

    const-wide/16 v0, 0x0

    :goto_3
    iput-wide v0, p0, Lolive/app/OliveMediaService;->durationMs:J

    .line 260
    const-string v0, "relativeTimePosition"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lolive/app/OliveMediaService;->parseTime(Ljava/lang/String;)J

    move-result-wide v0

    iput-wide v0, p0, Lolive/app/OliveMediaService;->positionMs:J

    .line 261
    if-eqz p2, :cond_0

    iget-object v0, p0, Lolive/app/OliveMediaService;->artUrl:Ljava/lang/String;

    invoke-virtual {p2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 262
    iput-object p2, p0, Lolive/app/OliveMediaService;->artUrl:Ljava/lang/String;

    .line 263
    iput-object p3, p0, Lolive/app/OliveMediaService;->art:Landroid/graphics/Bitmap;

    .line 266
    :cond_0
    :try_start_0
    const-string v0, "volVal"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    iput v0, p0, Lolive/app/OliveMediaService;->volumePercent:I
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 270
    :goto_4
    invoke-direct {p0}, Lolive/app/OliveMediaService;->publish()V

    .line 271
    return-void

    .line 255
    :cond_1
    const/4 v0, 0x0

    goto :goto_0

    .line 257
    :cond_2
    const-string v1, "artist"

    invoke-static {p1, v1}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    goto :goto_1

    .line 258
    :cond_3
    const-string v1, "album"

    invoke-static {p1, v1}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    goto :goto_2

    .line 259
    :cond_4
    const-string v0, "currentTrackDuration"

    invoke-static {p1, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lolive/app/OliveMediaService;->parseTime(Ljava/lang/String;)J

    move-result-wide v0

    goto :goto_3

    .line 267
    :catch_0
    move-exception v0

    .line 268
    const/4 v0, -0x1

    iput v0, p0, Lolive/app/OliveMediaService;->volumePercent:I

    goto :goto_4
.end method

.method private buildNotification()Landroid/app/Notification;
    .locals 8

    .prologue
    const v2, 0x1080024

    const/4 v7, 0x1

    .line 357
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_2

    new-instance v0, Landroid/app/Notification$Builder;

    const-string v1, "olive_playback"

    invoke-direct {v0, p0, v1}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    .line 358
    :goto_0
    invoke-virtual {p0}, Lolive/app/OliveMediaService;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const-string v3, "ic_m3_now_playing"

    const-string v4, "drawable"

    invoke-virtual {p0}, Lolive/app/OliveMediaService;->getPackageName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v1, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    .line 359
    if-eqz v1, :cond_3

    :goto_1
    invoke-virtual {v0, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v3

    .line 360
    iget-object v1, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_4

    iget-object v1, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    :goto_2
    invoke-virtual {v3, v1}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v3

    .line 361
    iget-object v1, p0, Lolive/app/OliveMediaService;->album:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_5

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v4, p0, Lolive/app/OliveMediaService;->artist:Ljava/lang/String;

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v4, " \u2022 "

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v4, p0, Lolive/app/OliveMediaService;->album:Ljava/lang/String;

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    :goto_3
    invoke-virtual {v3, v1}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v1

    iget-object v3, p0, Lolive/app/OliveMediaService;->art:Landroid/graphics/Bitmap;

    .line 362
    invoke-virtual {v1, v3}, Landroid/app/Notification$Builder;->setLargeIcon(Landroid/graphics/Bitmap;)Landroid/app/Notification$Builder;

    move-result-object v1

    .line 363
    invoke-direct {p0}, Lolive/app/OliveMediaService;->openPlayer()Landroid/app/PendingIntent;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    move-result-object v1

    .line 364
    invoke-virtual {v1, v7}, Landroid/app/Notification$Builder;->setVisibility(I)Landroid/app/Notification$Builder;

    move-result-object v1

    const/4 v3, 0x0

    .line 365
    invoke-virtual {v1, v3}, Landroid/app/Notification$Builder;->setShowWhen(Z)Landroid/app/Notification$Builder;

    move-result-object v1

    .line 366
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v3

    invoke-virtual {v1, v3}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    move-result-object v1

    new-instance v3, Landroid/app/Notification$Action;

    const v4, 0x1080025

    const-string v5, "Previous"

    const-string v6, "olive.app.PREVIOUS"

    .line 367
    invoke-direct {p0, v6}, Lolive/app/OliveMediaService;->serviceAction(Ljava/lang/String;)Landroid/app/PendingIntent;

    move-result-object v6

    invoke-direct {v3, v4, v5, v6}, Landroid/app/Notification$Action;-><init>(ILjava/lang/CharSequence;Landroid/app/PendingIntent;)V

    invoke-virtual {v1, v3}, Landroid/app/Notification$Builder;->addAction(Landroid/app/Notification$Action;)Landroid/app/Notification$Builder;

    move-result-object v3

    new-instance v4, Landroid/app/Notification$Action;

    .line 368
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v1

    if-eqz v1, :cond_0

    const v2, 0x1080023

    .line 369
    :cond_0
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v1

    if-eqz v1, :cond_6

    const-string v1, "Pause"

    :goto_4
    const-string v5, "olive.app.TOGGLE"

    invoke-direct {p0, v5}, Lolive/app/OliveMediaService;->serviceAction(Ljava/lang/String;)Landroid/app/PendingIntent;

    move-result-object v5

    invoke-direct {v4, v2, v1, v5}, Landroid/app/Notification$Action;-><init>(ILjava/lang/CharSequence;Landroid/app/PendingIntent;)V

    .line 368
    invoke-virtual {v3, v4}, Landroid/app/Notification$Builder;->addAction(Landroid/app/Notification$Action;)Landroid/app/Notification$Builder;

    move-result-object v1

    new-instance v2, Landroid/app/Notification$Action;

    const v3, 0x1080022

    const-string v4, "Next"

    const-string v5, "olive.app.NEXT"

    .line 370
    invoke-direct {p0, v5}, Lolive/app/OliveMediaService;->serviceAction(Ljava/lang/String;)Landroid/app/PendingIntent;

    move-result-object v5

    invoke-direct {v2, v3, v4, v5}, Landroid/app/Notification$Action;-><init>(ILjava/lang/CharSequence;Landroid/app/PendingIntent;)V

    invoke-virtual {v1, v2}, Landroid/app/Notification$Builder;->addAction(Landroid/app/Notification$Action;)Landroid/app/Notification$Builder;

    move-result-object v1

    new-instance v2, Landroid/app/Notification$MediaStyle;

    invoke-direct {v2}, Landroid/app/Notification$MediaStyle;-><init>()V

    iget-object v3, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    .line 371
    invoke-virtual {v3}, Landroid/media/session/MediaSession;->getSessionToken()Landroid/media/session/MediaSession$Token;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/app/Notification$MediaStyle;->setMediaSession(Landroid/media/session/MediaSession$Token;)Landroid/app/Notification$MediaStyle;

    move-result-object v2

    const/4 v3, 0x3

    new-array v3, v3, [I

    fill-array-data v3, :array_0

    invoke-virtual {v2, v3}, Landroid/app/Notification$MediaStyle;->setShowActionsInCompactView([I)Landroid/app/Notification$MediaStyle;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/app/Notification$Builder;->setStyle(Landroid/app/Notification$Style;)Landroid/app/Notification$Builder;

    .line 372
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1f

    if-lt v1, v2, :cond_1

    .line 373
    invoke-virtual {v0, v7}, Landroid/app/Notification$Builder;->setForegroundServiceBehavior(I)Landroid/app/Notification$Builder;

    .line 375
    :cond_1
    invoke-virtual {v0}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object v0

    return-object v0

    .line 357
    :cond_2
    new-instance v0, Landroid/app/Notification$Builder;

    invoke-direct {v0, p0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    goto/16 :goto_0

    :cond_3
    move v1, v2

    .line 359
    goto/16 :goto_1

    .line 360
    :cond_4
    const-string v1, "Olive"

    goto/16 :goto_2

    .line 361
    :cond_5
    iget-object v1, p0, Lolive/app/OliveMediaService;->artist:Ljava/lang/String;

    goto/16 :goto_3

    .line 369
    :cond_6
    const-string v1, "Play"

    goto :goto_4

    .line 371
    :array_0
    .array-data 4
        0x0
        0x1
        0x2
    .end array-data
.end method

.method private command(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3

    .prologue
    .line 198
    if-eqz p1, :cond_0

    iget-object v0, p0, Lolive/app/OliveMediaService;->devIP:Ljava/lang/String;

    if-nez v0, :cond_1

    .line 212
    :cond_0
    :goto_0
    return-void

    .line 201
    :cond_1
    if-eqz p2, :cond_2

    .line 202
    iput-object p2, p0, Lolive/app/OliveMediaService;->transport:Ljava/lang/String;

    .line 203
    invoke-direct {p0}, Lolive/app/OliveMediaService;->publish()V

    .line 205
    :cond_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "http://"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveMediaService;->devIP:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/includes/ajax/a_executeOperation.php?action="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 206
    iget-object v1, p0, Lolive/app/OliveMediaService;->io:Ljava/util/concurrent/ExecutorService;

    new-instance v2, Lolive/app/OliveMediaService$4;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveMediaService$4;-><init>(Lolive/app/OliveMediaService;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V

    .line 211
    const-wide/16 v0, 0x2bc

    invoke-direct {p0, v0, v1}, Lolive/app/OliveMediaService;->pollSoon(J)V

    goto :goto_0
.end method

.method public static ensureRunning(Landroid/app/Activity;)V
    .locals 3

    .prologue
    .line 83
    const-string v0, "Olive_Settings"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    const-string v1, "devIP"

    const-string v2, "none"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 84
    if-eqz v0, :cond_0

    const-string v1, "none"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_1

    .line 98
    :cond_0
    :goto_0
    return-void

    .line 88
    :cond_1
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    const-class v1, Lolive/app/OliveMediaService;

    invoke-direct {v0, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 89
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1a

    if-lt v1, v2, :cond_2

    .line 90
    invoke-virtual {p0, v0}, Landroid/app/Activity;->startForegroundService(Landroid/content/Intent;)Landroid/content/ComponentName;
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 94
    :catch_0
    move-exception v0

    .line 96
    invoke-virtual {v0}, Ljava/lang/RuntimeException;->printStackTrace()V

    goto :goto_0

    .line 92
    :cond_2
    :try_start_1
    invoke-virtual {p0, v0}, Landroid/app/Activity;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;
    :try_end_1
    .catch Ljava/lang/RuntimeException; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0
.end method

.method private fetch()V
    .locals 3

    .prologue
    .line 220
    const-string v0, "Olive_Settings"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lolive/app/OliveMediaService;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 221
    const-string v1, "devIP"

    const-string v2, "none"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 222
    if-eqz v0, :cond_0

    const-string v1, "none"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 223
    :cond_0
    invoke-virtual {p0}, Lolive/app/OliveMediaService;->stopSelf()V

    .line 250
    :goto_0
    return-void

    .line 226
    :cond_1
    iput-object v0, p0, Lolive/app/OliveMediaService;->devIP:Ljava/lang/String;

    .line 227
    iget-object v1, p0, Lolive/app/OliveMediaService;->io:Ljava/util/concurrent/ExecutorService;

    new-instance v2, Lolive/app/OliveMediaService$5;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveMediaService$5;-><init>(Lolive/app/OliveMediaService;Ljava/lang/String;)V

    invoke-interface {v1, v2}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V

    goto :goto_0
.end method

.method private isPaused()Z
    .locals 2

    .prologue
    .line 189
    const-string v0, "PAUSED_PLAYBACK"

    iget-object v1, p0, Lolive/app/OliveMediaService;->transport:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method private isPlaying()Z
    .locals 2

    .prologue
    .line 185
    const-string v0, "PLAYING"

    iget-object v1, p0, Lolive/app/OliveMediaService;->transport:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method private isStopped()Z
    .locals 1

    .prologue
    .line 193
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPaused()Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private static loadArt(Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 6

    .prologue
    const/4 v1, 0x0

    .line 417
    .line 419
    :try_start_0
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 420
    const/16 v2, 0x1388

    :try_start_1
    invoke-virtual {v0, v2}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 421
    const/16 v2, 0x1388

    invoke-virtual {v0, v2}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 422
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v2

    invoke-static {v2}, Landroid/graphics/BitmapFactory;->decodeStream(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    move-result-object v2

    .line 423
    if-nez v2, :cond_1

    .line 435
    if-eqz v0, :cond_0

    .line 436
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_0
    move-object v0, v1

    .line 433
    :goto_0
    return-object v0

    .line 426
    :cond_1
    :try_start_2
    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    invoke-static {v3, v4}, Ljava/lang/Math;->max(II)I
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    move-result v3

    .line 427
    const/16 v4, 0x200

    if-gt v3, v4, :cond_3

    .line 435
    if-eqz v0, :cond_2

    .line 436
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_2
    move-object v0, v2

    .line 428
    goto :goto_0

    .line 430
    :cond_3
    const/high16 v4, 0x44000000    # 512.0f

    int-to-float v3, v3

    div-float v3, v4, v3

    .line 431
    :try_start_3
    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v4

    int-to-float v4, v4

    mul-float/2addr v4, v3

    invoke-static {v4}, Ljava/lang/Math;->round(F)I

    move-result v4

    invoke-virtual {v2}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v5

    int-to-float v5, v5

    mul-float/2addr v3, v5

    invoke-static {v3}, Ljava/lang/Math;->round(F)I

    move-result v3

    const/4 v5, 0x1

    invoke-static {v2, v4, v3, v5}, Landroid/graphics/Bitmap;->createScaledBitmap(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_1
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    move-result-object v1

    .line 435
    if-eqz v0, :cond_4

    .line 436
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_4
    move-object v0, v1

    .line 431
    goto :goto_0

    .line 432
    :catch_0
    move-exception v0

    move-object v0, v1

    .line 435
    :goto_1
    if-eqz v0, :cond_5

    .line 436
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_5
    move-object v0, v1

    .line 433
    goto :goto_0

    .line 435
    :catchall_0
    move-exception v0

    move-object v2, v0

    move-object v3, v1

    :goto_2
    if-eqz v3, :cond_6

    .line 436
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 438
    :cond_6
    throw v2

    .line 435
    :catchall_1
    move-exception v1

    move-object v2, v1

    move-object v3, v0

    goto :goto_2

    .line 432
    :catch_1
    move-exception v2

    goto :goto_1
.end method

.method private openPlayer()Landroid/app/PendingIntent;
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 385
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "olive.app.LoadTabs"

    invoke-virtual {v0, p0, v1}, Landroid/content/Intent;->setClassName(Landroid/content/Context;Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v0

    const/high16 v1, 0x10000000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    move-result-object v0

    .line 386
    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    const-string v2, "olive.app.Playscreen"

    invoke-virtual {v1, p0, v2}, Landroid/content/Intent;->setClassName(Landroid/content/Context;Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v1

    .line 387
    const/4 v2, 0x2

    new-array v2, v2, [Landroid/content/Intent;

    aput-object v0, v2, v3

    const/4 v0, 0x1

    aput-object v1, v2, v0

    const/high16 v0, 0xc000000

    invoke-static {p0, v3, v2, v0}, Landroid/app/PendingIntent;->getActivities(Landroid/content/Context;I[Landroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v0

    return-object v0
.end method

.method static parseTime(Ljava/lang/String;)J
    .locals 10

    .prologue
    const-wide/16 v0, 0x0

    const/4 v2, 0x0

    .line 453
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    const-string v4, ":"

    invoke-virtual {v3, v4}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v3

    .line 456
    :try_start_0
    array-length v6, v3

    move-wide v4, v0

    :goto_0
    if-ge v2, v6, :cond_0

    aget-object v7, v3, v2

    .line 457
    const-wide/16 v8, 0x3c

    mul-long/2addr v4, v8

    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v7

    const-string v8, "\\."

    invoke-virtual {v7, v8}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    const/4 v8, 0x0

    aget-object v7, v7, v8

    invoke-static {v7}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-wide v8

    add-long/2addr v4, v8

    .line 456
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 462
    :cond_0
    const-wide/16 v0, 0x3e8

    mul-long/2addr v0, v4

    :goto_1
    return-wide v0

    .line 459
    :catch_0
    move-exception v2

    goto :goto_1
.end method

.method private pollSoon(J)V
    .locals 3

    .prologue
    .line 215
    iget-object v0, p0, Lolive/app/OliveMediaService;->main:Landroid/os/Handler;

    iget-object v1, p0, Lolive/app/OliveMediaService;->poll:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 216
    iget-object v0, p0, Lolive/app/OliveMediaService;->main:Landroid/os/Handler;

    iget-object v1, p0, Lolive/app/OliveMediaService;->poll:Ljava/lang/Runnable;

    invoke-virtual {v0, v1, p1, p2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 217
    return-void
.end method

.method private publish()V
    .locals 8

    .prologue
    const/4 v2, 0x1

    .line 296
    new-instance v1, Landroid/media/MediaMetadata$Builder;

    invoke-direct {v1}, Landroid/media/MediaMetadata$Builder;-><init>()V

    const-string v3, "android.media.metadata.TITLE"

    .line 297
    iget-object v0, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-lez v0, :cond_1

    iget-object v0, p0, Lolive/app/OliveMediaService;->title:Ljava/lang/String;

    :goto_0
    invoke-virtual {v1, v3, v0}, Landroid/media/MediaMetadata$Builder;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/media/MediaMetadata$Builder;

    move-result-object v0

    const-string v1, "android.media.metadata.ARTIST"

    iget-object v3, p0, Lolive/app/OliveMediaService;->artist:Ljava/lang/String;

    .line 298
    invoke-virtual {v0, v1, v3}, Landroid/media/MediaMetadata$Builder;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/media/MediaMetadata$Builder;

    move-result-object v0

    const-string v1, "android.media.metadata.ALBUM"

    iget-object v3, p0, Lolive/app/OliveMediaService;->album:Ljava/lang/String;

    .line 299
    invoke-virtual {v0, v1, v3}, Landroid/media/MediaMetadata$Builder;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/media/MediaMetadata$Builder;

    move-result-object v3

    const-string v4, "android.media.metadata.DURATION"

    .line 300
    iget-wide v0, p0, Lolive/app/OliveMediaService;->durationMs:J

    const-wide/16 v6, 0x0

    cmp-long v0, v0, v6

    if-lez v0, :cond_2

    iget-wide v0, p0, Lolive/app/OliveMediaService;->durationMs:J

    :goto_1
    invoke-virtual {v3, v4, v0, v1}, Landroid/media/MediaMetadata$Builder;->putLong(Ljava/lang/String;J)Landroid/media/MediaMetadata$Builder;

    move-result-object v0

    .line 301
    iget-object v1, p0, Lolive/app/OliveMediaService;->art:Landroid/graphics/Bitmap;

    if-eqz v1, :cond_0

    .line 302
    const-string v1, "android.media.metadata.ALBUM_ART"

    iget-object v3, p0, Lolive/app/OliveMediaService;->art:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1, v3}, Landroid/media/MediaMetadata$Builder;->putBitmap(Ljava/lang/String;Landroid/graphics/Bitmap;)Landroid/media/MediaMetadata$Builder;

    .line 304
    :cond_0
    invoke-virtual {v0}, Landroid/media/MediaMetadata$Builder;->build()Landroid/media/MediaMetadata;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveMediaService;->lastMetadata:Landroid/media/MediaMetadata;

    .line 305
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    iget-object v1, p0, Lolive/app/OliveMediaService;->lastMetadata:Landroid/media/MediaMetadata;

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setMetadata(Landroid/media/MediaMetadata;)V

    .line 306
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_3

    const/4 v0, 0x3

    .line 307
    :goto_2
    const-wide/16 v4, 0x237

    .line 309
    new-instance v1, Landroid/media/session/PlaybackState$Builder;

    invoke-direct {v1}, Landroid/media/session/PlaybackState$Builder;-><init>()V

    .line 310
    invoke-virtual {v1, v4, v5}, Landroid/media/session/PlaybackState$Builder;->setActions(J)Landroid/media/session/PlaybackState$Builder;

    move-result-object v3

    iget-wide v4, p0, Lolive/app/OliveMediaService;->positionMs:J

    .line 311
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v1

    if-eqz v1, :cond_5

    const/high16 v1, 0x3f800000    # 1.0f

    :goto_3
    invoke-virtual {v3, v0, v4, v5, v1}, Landroid/media/session/PlaybackState$Builder;->setState(IJF)Landroid/media/session/PlaybackState$Builder;

    move-result-object v0

    .line 312
    invoke-virtual {v0}, Landroid/media/session/PlaybackState$Builder;->build()Landroid/media/session/PlaybackState;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveMediaService;->lastState:Landroid/media/session/PlaybackState;

    .line 313
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    iget-object v1, p0, Lolive/app/OliveMediaService;->lastState:Landroid/media/session/PlaybackState;

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setPlaybackState(Landroid/media/session/PlaybackState;)V

    .line 314
    invoke-direct {p0}, Lolive/app/OliveMediaService;->updateVolumeProvider()V

    .line 315
    const-string v0, "notification"

    invoke-virtual {p0, v0}, Lolive/app/OliveMediaService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/NotificationManager;

    invoke-direct {p0}, Lolive/app/OliveMediaService;->buildNotification()Landroid/app/Notification;

    move-result-object v1

    invoke-virtual {v0, v2, v1}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V

    .line 316
    return-void

    .line 297
    :cond_1
    const-string v0, "Olive"

    goto :goto_0

    .line 300
    :cond_2
    const-wide/16 v0, -0x1

    goto :goto_1

    .line 306
    :cond_3
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPaused()Z

    move-result v0

    if-eqz v0, :cond_4

    const/4 v0, 0x2

    goto :goto_2

    :cond_4
    move v0, v2

    goto :goto_2

    .line 311
    :cond_5
    const/4 v1, 0x0

    goto :goto_3
.end method

.method static read(Ljava/lang/String;I)Ljava/lang/String;
    .locals 8

    .prologue
    const/4 v1, 0x0

    .line 393
    .line 395
    :try_start_0
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 396
    :try_start_1
    invoke-virtual {v0, p1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 397
    invoke-virtual {v0, p1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 398
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v2

    .line 399
    new-instance v3, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v3}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 400
    const/16 v4, 0x2000

    new-array v4, v4, [B

    .line 402
    :goto_0
    invoke-virtual {v2, v4}, Ljava/io/InputStream;->read([B)I

    move-result v5

    if-lez v5, :cond_1

    invoke-virtual {v3}, Ljava/io/ByteArrayOutputStream;->size()I

    move-result v6

    const/high16 v7, 0x100000

    if-ge v6, v7, :cond_1

    .line 403
    const/4 v6, 0x0

    invoke-virtual {v3, v4, v6, v5}, Ljava/io/ByteArrayOutputStream;->write([BII)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    .line 407
    :catch_0
    move-exception v2

    .line 410
    :goto_1
    if-eqz v0, :cond_0

    .line 411
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_0
    move-object v0, v1

    .line 408
    :goto_2
    return-object v0

    .line 405
    :cond_1
    :try_start_2
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V

    .line 406
    const-string v2, "UTF-8"

    invoke-virtual {v3, v2}, Ljava/io/ByteArrayOutputStream;->toString(Ljava/lang/String;)Ljava/lang/String;
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_0
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    move-result-object v1

    .line 410
    if-eqz v0, :cond_2

    .line 411
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    :cond_2
    move-object v0, v1

    .line 406
    goto :goto_2

    .line 410
    :catchall_0
    move-exception v0

    move-object v2, v0

    move-object v3, v1

    :goto_3
    if-eqz v3, :cond_3

    .line 411
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 413
    :cond_3
    throw v2

    .line 410
    :catchall_1
    move-exception v1

    move-object v2, v1

    move-object v3, v0

    goto :goto_3

    .line 407
    :catch_1
    move-exception v0

    move-object v0, v1

    goto :goto_1
.end method

.method private scheduleNext(Z)V
    .locals 6

    .prologue
    const-wide/16 v4, 0x0

    .line 274
    const-string v0, "power"

    invoke-virtual {p0, v0}, Lolive/app/OliveMediaService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/PowerManager;

    invoke-virtual {v0}, Landroid/os/PowerManager;->isInteractive()Z

    move-result v0

    .line 275
    if-nez p1, :cond_0

    sget-boolean v1, Lolive/app/OliveMediaService;->appVisible:Z

    if-nez v1, :cond_0

    .line 277
    invoke-virtual {p0}, Lolive/app/OliveMediaService;->stopSelf()V

    .line 292
    :goto_0
    return-void

    .line 280
    :cond_0
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isStopped()Z

    move-result v1

    if-eqz v1, :cond_3

    sget-boolean v1, Lolive/app/OliveMediaService;->appVisible:Z

    if-nez v1, :cond_3

    .line 281
    iget-wide v2, p0, Lolive/app/OliveMediaService;->stoppedSince:J

    cmp-long v1, v2, v4

    if-nez v1, :cond_2

    .line 282
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    iput-wide v2, p0, Lolive/app/OliveMediaService;->stoppedSince:J

    .line 290
    :cond_1
    :goto_1
    if-nez p1, :cond_4

    const-wide/16 v0, 0x3a98

    .line 291
    :goto_2
    invoke-direct {p0, v0, v1}, Lolive/app/OliveMediaService;->pollSoon(J)V

    goto :goto_0

    .line 283
    :cond_2
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    iget-wide v4, p0, Lolive/app/OliveMediaService;->stoppedSince:J

    sub-long/2addr v2, v4

    const-wide/32 v4, 0x927c0

    cmp-long v1, v2, v4

    if-lez v1, :cond_1

    .line 284
    invoke-virtual {p0}, Lolive/app/OliveMediaService;->stopSelf()V

    goto :goto_0

    .line 288
    :cond_3
    iput-wide v4, p0, Lolive/app/OliveMediaService;->stoppedSince:J

    goto :goto_1

    .line 290
    :cond_4
    if-nez v0, :cond_5

    const-wide/16 v0, 0x7530

    goto :goto_2

    :cond_5
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_6

    const-wide/16 v0, 0xbb8

    goto :goto_2

    :cond_6
    const-wide/16 v0, 0x1770

    goto :goto_2
.end method

.method private serviceAction(Ljava/lang/String;)Landroid/app/PendingIntent;
    .locals 3

    .prologue
    .line 379
    new-instance v0, Landroid/content/Intent;

    const-class v1, Lolive/app/OliveMediaService;

    invoke-direct {v0, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    invoke-virtual {v0, p1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v0

    .line 380
    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result v1

    const/high16 v2, 0xc000000

    invoke-static {p0, v1, v0, v2}, Landroid/app/PendingIntent;->getService(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v0

    return-object v0
.end method

.method private setOliveVolume(I)V
    .locals 3

    .prologue
    .line 340
    const/4 v0, 0x0

    const/16 v1, 0x64

    invoke-static {v1, p1}, Ljava/lang/Math;->min(II)I

    move-result v1

    invoke-static {v0, v1}, Ljava/lang/Math;->max(II)I

    move-result v0

    .line 341
    iput v0, p0, Lolive/app/OliveMediaService;->volumePercent:I

    .line 342
    iget-object v1, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    invoke-virtual {v1, v0}, Landroid/media/VolumeProvider;->setCurrentVolume(I)V

    .line 343
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "volumeSet&volume="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "&mutestat=1"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lolive/app/OliveMediaService;->command(Ljava/lang/String;Ljava/lang/String;)V

    .line 344
    return-void
.end method

.method private startInForeground()V
    .locals 4

    .prologue
    const/4 v3, 0x1

    .line 348
    invoke-direct {p0}, Lolive/app/OliveMediaService;->buildNotification()Landroid/app/Notification;

    move-result-object v0

    .line 349
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1d

    if-lt v1, v2, :cond_0

    .line 350
    const/4 v1, 0x2

    invoke-virtual {p0, v3, v0, v1}, Lolive/app/OliveMediaService;->startForeground(ILandroid/app/Notification;I)V

    .line 354
    :goto_0
    return-void

    .line 352
    :cond_0
    invoke-virtual {p0, v3, v0}, Lolive/app/OliveMediaService;->startForeground(ILandroid/app/Notification;)V

    goto :goto_0
.end method

.method static tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    .prologue
    .line 443
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "<"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ">([^<]*)</"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ">"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x2

    invoke-static {v0, v1}, Ljava/util/regex/Pattern;->compile(Ljava/lang/String;I)Ljava/util/regex/Pattern;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/util/regex/Pattern;->matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;

    move-result-object v0

    .line 444
    invoke-virtual {v0}, Ljava/util/regex/Matcher;->find()Z

    move-result v1

    if-nez v1, :cond_0

    .line 445
    const-string v0, ""

    .line 447
    :goto_0
    return-object v0

    :cond_0
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/util/regex/Matcher;->group(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    const-string v1, "&amp;"

    const-string v2, "&"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "&lt;"

    const-string v2, "<"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "&gt;"

    const-string v2, ">"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "&quot;"

    const-string v2, "\""

    .line 448
    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "&apos;"

    const-string v2, "\'"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "&#39;"

    const-string v2, "\'"

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0
.end method

.method private updateVolumeProvider()V
    .locals 4

    .prologue
    .line 320
    iget v0, p0, Lolive/app/OliveMediaService;->volumePercent:I

    if-gez v0, :cond_1

    .line 337
    :cond_0
    :goto_0
    return-void

    .line 323
    :cond_1
    iget-object v0, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    if-nez v0, :cond_2

    .line 324
    new-instance v0, Lolive/app/OliveMediaService$6;

    const/4 v1, 0x2

    const/16 v2, 0x64

    iget v3, p0, Lolive/app/OliveMediaService;->volumePercent:I

    invoke-direct {v0, p0, v1, v2, v3}, Lolive/app/OliveMediaService$6;-><init>(Lolive/app/OliveMediaService;III)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    .line 333
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    iget-object v1, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setPlaybackToRemote(Landroid/media/VolumeProvider;)V

    goto :goto_0

    .line 334
    :cond_2
    iget-object v0, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    invoke-virtual {v0}, Landroid/media/VolumeProvider;->getCurrentVolume()I

    move-result v0

    iget v1, p0, Lolive/app/OliveMediaService;->volumePercent:I

    if-eq v0, v1, :cond_0

    .line 335
    iget-object v0, p0, Lolive/app/OliveMediaService;->volume:Landroid/media/VolumeProvider;

    iget v1, p0, Lolive/app/OliveMediaService;->volumePercent:I

    invoke-virtual {v0, v1}, Landroid/media/VolumeProvider;->setCurrentVolume(I)V

    goto :goto_0
.end method


# virtual methods
.method public onBind(Landroid/content/Intent;)Landroid/os/IBinder;
    .locals 1

    .prologue
    .line 179
    const/4 v0, 0x0

    return-object v0
.end method

.method public onCreate()V
    .locals 4

    .prologue
    .line 102
    invoke-super {p0}, Landroid/app/Service;->onCreate()V

    .line 103
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 104
    new-instance v1, Landroid/app/NotificationChannel;

    const-string v0, "olive_playback"

    const-string v2, "Now playing"

    const/4 v3, 0x2

    invoke-direct {v1, v0, v2, v3}, Landroid/app/NotificationChannel;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V

    .line 105
    const-string v0, "Playback controls for your Olive"

    invoke-virtual {v1, v0}, Landroid/app/NotificationChannel;->setDescription(Ljava/lang/String;)V

    .line 106
    const/4 v0, 0x0

    invoke-virtual {v1, v0}, Landroid/app/NotificationChannel;->setShowBadge(Z)V

    .line 107
    const-string v0, "notification"

    invoke-virtual {p0, v0}, Lolive/app/OliveMediaService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/NotificationManager;

    invoke-virtual {v0, v1}, Landroid/app/NotificationManager;->createNotificationChannel(Landroid/app/NotificationChannel;)V

    .line 109
    :cond_0
    new-instance v0, Landroid/media/session/MediaSession;

    const-string v1, "Olive"

    invoke-direct {v0, p0, v1}, Landroid/media/session/MediaSession;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    .line 110
    new-instance v0, Lolive/app/OliveMediaService$2;

    invoke-direct {v0, p0}, Lolive/app/OliveMediaService$2;-><init>(Lolive/app/OliveMediaService;)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->callback:Landroid/media/session/MediaSession$Callback;

    .line 131
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    iget-object v1, p0, Lolive/app/OliveMediaService;->callback:Landroid/media/session/MediaSession$Callback;

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setCallback(Landroid/media/session/MediaSession$Callback;)V

    .line 132
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-direct {p0}, Lolive/app/OliveMediaService;->openPlayer()Landroid/app/PendingIntent;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setSessionActivity(Landroid/app/PendingIntent;)V

    .line 133
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setActive(Z)V

    .line 134
    new-instance v0, Lolive/app/OliveMediaService$3;

    invoke-direct {v0, p0}, Lolive/app/OliveMediaService$3;-><init>(Lolive/app/OliveMediaService;)V

    iput-object v0, p0, Lolive/app/OliveMediaService;->screenOn:Landroid/content/BroadcastReceiver;

    .line 139
    iget-object v0, p0, Lolive/app/OliveMediaService;->screenOn:Landroid/content/BroadcastReceiver;

    new-instance v1, Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.SCREEN_ON"

    invoke-direct {v1, v2}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0, v0, v1}, Lolive/app/OliveMediaService;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 140
    invoke-direct {p0}, Lolive/app/OliveMediaService;->startInForeground()V

    .line 141
    return-void
.end method

.method public onDestroy()V
    .locals 2

    .prologue
    .line 166
    iget-object v0, p0, Lolive/app/OliveMediaService;->main:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 167
    iget-object v0, p0, Lolive/app/OliveMediaService;->io:Ljava/util/concurrent/ExecutorService;

    invoke-interface {v0}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 169
    :try_start_0
    iget-object v0, p0, Lolive/app/OliveMediaService;->screenOn:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v0}, Lolive/app/OliveMediaService;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    .line 172
    :goto_0
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSession;->setActive(Z)V

    .line 173
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-virtual {v0}, Landroid/media/session/MediaSession;->release()V

    .line 174
    invoke-super {p0}, Landroid/app/Service;->onDestroy()V

    .line 175
    return-void

    .line 170
    :catch_0
    move-exception v0

    goto :goto_0
.end method

.method public onStartCommand(Landroid/content/Intent;II)I
    .locals 2

    .prologue
    .line 145
    invoke-direct {p0}, Lolive/app/OliveMediaService;->startInForeground()V

    .line 146
    if-nez p1, :cond_0

    const/4 v0, 0x0

    .line 147
    :goto_0
    const-string v1, "olive.app.PREVIOUS"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 148
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-virtual {v0}, Landroid/media/session/MediaSession;->getController()Landroid/media/session/MediaController;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController$TransportControls;->skipToPrevious()V

    .line 161
    :goto_1
    const/4 v0, 0x2

    return v0

    .line 146
    :cond_0
    invoke-virtual {p1}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    .line 149
    :cond_1
    const-string v1, "olive.app.NEXT"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 150
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-virtual {v0}, Landroid/media/session/MediaSession;->getController()Landroid/media/session/MediaController;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController$TransportControls;->skipToNext()V

    goto :goto_1

    .line 151
    :cond_2
    const-string v1, "olive.app.TOGGLE"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 152
    invoke-direct {p0}, Lolive/app/OliveMediaService;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_3

    .line 153
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-virtual {v0}, Landroid/media/session/MediaSession;->getController()Landroid/media/session/MediaController;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController$TransportControls;->pause()V

    goto :goto_1

    .line 155
    :cond_3
    iget-object v0, p0, Lolive/app/OliveMediaService;->session:Landroid/media/session/MediaSession;

    invoke-virtual {v0}, Landroid/media/session/MediaSession;->getController()Landroid/media/session/MediaController;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/session/MediaController$TransportControls;->play()V

    goto :goto_1

    .line 158
    :cond_4
    const-wide/16 v0, 0x0

    invoke-direct {p0, v0, v1}, Lolive/app/OliveMediaService;->pollSoon(J)V

    goto :goto_1
.end method
