.class Lolive/app/OliveMediaService$1;
.super Ljava/lang/Object;
.source "OliveMediaService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveMediaService;
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
    .line 78
    iput-object p1, p0, Lolive/app/OliveMediaService$1;->this$0:Lolive/app/OliveMediaService;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .prologue
    .line 80
    iget-object v0, p0, Lolive/app/OliveMediaService$1;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v0}, Lolive/app/OliveMediaService;->access$000(Lolive/app/OliveMediaService;)V

    .line 81
    return-void
.end method
