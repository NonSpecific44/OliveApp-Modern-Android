.class Lolive/app/OliveMediaService$3;
.super Landroid/content/BroadcastReceiver;
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
    .line 137
    iput-object p1, p0, Lolive/app/OliveMediaService$3;->this$0:Lolive/app/OliveMediaService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4

    .prologue
    .line 139
    iget-object v0, p0, Lolive/app/OliveMediaService$3;->this$0:Lolive/app/OliveMediaService;

    const-wide/16 v2, 0x0

    invoke-static {v0, v2, v3}, Lolive/app/OliveMediaService;->access$600(Lolive/app/OliveMediaService;J)V

    .line 140
    return-void
.end method
