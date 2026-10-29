.class Lolive/app/OliveMediaService$5$1;
.super Ljava/lang/Object;
.source "OliveMediaService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveMediaService$5;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lolive/app/OliveMediaService$5;

.field final synthetic val$fetchedArt:Landroid/graphics/Bitmap;

.field final synthetic val$fetchedArtUrl:Ljava/lang/String;

.field final synthetic val$xml:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveMediaService$5;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 240
    iput-object p1, p0, Lolive/app/OliveMediaService$5$1;->this$1:Lolive/app/OliveMediaService$5;

    iput-object p2, p0, Lolive/app/OliveMediaService$5$1;->val$xml:Ljava/lang/String;

    iput-object p3, p0, Lolive/app/OliveMediaService$5$1;->val$fetchedArtUrl:Ljava/lang/String;

    iput-object p4, p0, Lolive/app/OliveMediaService$5$1;->val$fetchedArt:Landroid/graphics/Bitmap;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 242
    iget-object v0, p0, Lolive/app/OliveMediaService$5$1;->val$xml:Ljava/lang/String;

    if-eqz v0, :cond_0

    .line 243
    iget-object v0, p0, Lolive/app/OliveMediaService$5$1;->this$1:Lolive/app/OliveMediaService$5;

    iget-object v0, v0, Lolive/app/OliveMediaService$5;->this$0:Lolive/app/OliveMediaService;

    iget-object v1, p0, Lolive/app/OliveMediaService$5$1;->val$xml:Ljava/lang/String;

    iget-object v2, p0, Lolive/app/OliveMediaService$5$1;->val$fetchedArtUrl:Ljava/lang/String;

    iget-object v3, p0, Lolive/app/OliveMediaService$5$1;->val$fetchedArt:Landroid/graphics/Bitmap;

    invoke-static {v0, v1, v2, v3}, Lolive/app/OliveMediaService;->access$900(Lolive/app/OliveMediaService;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V

    .line 245
    :cond_0
    iget-object v0, p0, Lolive/app/OliveMediaService$5$1;->this$1:Lolive/app/OliveMediaService$5;

    iget-object v1, v0, Lolive/app/OliveMediaService$5;->this$0:Lolive/app/OliveMediaService;

    iget-object v0, p0, Lolive/app/OliveMediaService$5$1;->val$xml:Ljava/lang/String;

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    :goto_0
    invoke-static {v1, v0}, Lolive/app/OliveMediaService;->access$1000(Lolive/app/OliveMediaService;Z)V

    .line 246
    return-void

    .line 245
    :cond_1
    const/4 v0, 0x0

    goto :goto_0
.end method
