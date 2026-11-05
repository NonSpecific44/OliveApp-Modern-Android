.class Lolive/app/OliveMediaService$2;
.super Landroid/media/session/MediaSession$Callback;
.source "OliveMediaService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveMediaService;->onCreate()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveMediaService;


# direct methods
.method constructor <init>(Lolive/app/OliveMediaService;)V
    .locals 0

    .prologue
    .line 113
    iput-object p1, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    invoke-direct {p0}, Landroid/media/session/MediaSession$Callback;-><init>()V

    return-void
.end method


# virtual methods
.method public onPause()V
    .locals 3

    .prologue
    .line 119
    iget-object v1, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v0}, Lolive/app/OliveMediaService;->access$500(Lolive/app/OliveMediaService;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0"

    :goto_0
    const-string v2, "PAUSED_PLAYBACK"

    invoke-static {v1, v0, v2}, Lolive/app/OliveMediaService;->access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V

    .line 120
    return-void

    .line 119
    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method public onPlay()V
    .locals 3

    .prologue
    .line 115
    iget-object v1, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v0}, Lolive/app/OliveMediaService;->access$100(Lolive/app/OliveMediaService;)Z

    move-result v0

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "controlPlayer&root=playItem&upnpid="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v2}, Lolive/app/OliveMediaService;->access$200(Lolive/app/OliveMediaService;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    :goto_0
    const-string v2, "PLAYING"

    invoke-static {v1, v0, v2}, Lolive/app/OliveMediaService;->access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V

    .line 116
    return-void

    .line 115
    :cond_0
    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v0}, Lolive/app/OliveMediaService;->access$300(Lolive/app/OliveMediaService;)Z

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0"

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    goto :goto_0
.end method

.method public onSkipToNext()V
    .locals 3

    .prologue
    .line 123
    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    const-string v1, "right_skip"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Lolive/app/OliveMediaService;->access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V

    .line 124
    return-void
.end method

.method public onSkipToPrevious()V
    .locals 3

    .prologue
    .line 127
    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    const-string v1, "left_skip"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Lolive/app/OliveMediaService;->access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V

    .line 128
    return-void
.end method

.method public onStop()V
    .locals 3

    .prologue
    .line 131
    iget-object v0, p0, Lolive/app/OliveMediaService$2;->this$0:Lolive/app/OliveMediaService;

    const-string v1, "controlPlayer&id=stop"

    const-string v2, "STOPPED"

    invoke-static {v0, v1, v2}, Lolive/app/OliveMediaService;->access$400(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;)V

    .line 132
    return-void
.end method
