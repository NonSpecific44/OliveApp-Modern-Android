.class Lolive/app/OliveMediaService$4;
.super Ljava/lang/Object;
.source "OliveMediaService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveMediaService;->command(Ljava/lang/String;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveMediaService;

.field final synthetic val$url:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveMediaService;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 206
    iput-object p1, p0, Lolive/app/OliveMediaService$4;->this$0:Lolive/app/OliveMediaService;

    iput-object p2, p0, Lolive/app/OliveMediaService$4;->val$url:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .prologue
    .line 208
    iget-object v0, p0, Lolive/app/OliveMediaService$4;->val$url:Ljava/lang/String;

    const/16 v1, 0xfa0

    invoke-static {v0, v1}, Lolive/app/OliveMediaService;->read(Ljava/lang/String;I)Ljava/lang/String;

    .line 209
    return-void
.end method
