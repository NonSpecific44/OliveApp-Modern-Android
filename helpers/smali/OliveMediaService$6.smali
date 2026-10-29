.class Lolive/app/OliveMediaService$6;
.super Landroid/media/VolumeProvider;
.source "OliveMediaService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveMediaService;->updateVolumeProvider()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveMediaService;


# direct methods
.method constructor <init>(Lolive/app/OliveMediaService;III)V
    .locals 0

    .prologue
    .line 324
    iput-object p1, p0, Lolive/app/OliveMediaService$6;->this$0:Lolive/app/OliveMediaService;

    invoke-direct {p0, p2, p3, p4}, Landroid/media/VolumeProvider;-><init>(III)V

    return-void
.end method


# virtual methods
.method public onAdjustVolume(I)V
    .locals 3

    .prologue
    .line 330
    iget-object v0, p0, Lolive/app/OliveMediaService$6;->this$0:Lolive/app/OliveMediaService;

    invoke-virtual {p0}, Lolive/app/OliveMediaService$6;->getCurrentVolume()I

    move-result v1

    mul-int/lit8 v2, p1, 0x5

    add-int/2addr v1, v2

    invoke-static {v0, v1}, Lolive/app/OliveMediaService;->access$1200(Lolive/app/OliveMediaService;I)V

    .line 331
    return-void
.end method

.method public onSetVolumeTo(I)V
    .locals 1

    .prologue
    .line 326
    iget-object v0, p0, Lolive/app/OliveMediaService$6;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v0, p1}, Lolive/app/OliveMediaService;->access$1200(Lolive/app/OliveMediaService;I)V

    .line 327
    return-void
.end method
