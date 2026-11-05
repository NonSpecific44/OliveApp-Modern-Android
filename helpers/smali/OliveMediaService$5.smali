.class Lolive/app/OliveMediaService$5;
.super Ljava/lang/Object;
.source "OliveMediaService.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveMediaService;->fetch()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveMediaService;

.field final synthetic val$ip:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveMediaService;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 241
    iput-object p1, p0, Lolive/app/OliveMediaService$5;->this$0:Lolive/app/OliveMediaService;

    iput-object p2, p0, Lolive/app/OliveMediaService$5;->val$ip:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .prologue
    const/4 v1, 0x0

    .line 243
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "http://"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lolive/app/OliveMediaService$5;->val$ip:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "/server/getMetadata.php"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const/16 v2, 0x1388

    invoke-static {v0, v2}, Lolive/app/OliveMediaService;->read(Ljava/lang/String;I)Ljava/lang/String;

    move-result-object v3

    .line 246
    if-eqz v3, :cond_1

    .line 247
    const-string v0, "albumart"

    invoke-static {v3, v0}, Lolive/app/OliveMediaService;->tag(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "127.0.0.1"

    iget-object v4, p0, Lolive/app/OliveMediaService$5;->val$ip:Ljava/lang/String;

    invoke-virtual {v0, v2, v4}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    .line 248
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v2

    if-lez v2, :cond_0

    iget-object v2, p0, Lolive/app/OliveMediaService$5;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v2}, Lolive/app/OliveMediaService;->access$700(Lolive/app/OliveMediaService;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 249
    invoke-static {v0}, Lolive/app/OliveMediaService;->access$800(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    move-object v2, v1

    .line 254
    :goto_0
    iget-object v1, p0, Lolive/app/OliveMediaService$5;->this$0:Lolive/app/OliveMediaService;

    invoke-static {v1}, Lolive/app/OliveMediaService;->access$1100(Lolive/app/OliveMediaService;)Landroid/os/Handler;

    move-result-object v1

    new-instance v4, Lolive/app/OliveMediaService$5$1;

    invoke-direct {v4, p0, v3, v0, v2}, Lolive/app/OliveMediaService$5$1;-><init>(Lolive/app/OliveMediaService$5;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;)V

    invoke-virtual {v1, v4}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 262
    return-void

    :cond_0
    move-object v2, v1

    goto :goto_0

    :cond_1
    move-object v0, v1

    move-object v2, v1

    goto :goto_0
.end method
