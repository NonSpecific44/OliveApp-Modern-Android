.class Lolive/app/OliveArt$1$1;
.super Ljava/lang/Object;
.source "OliveArt.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArt$1;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveArt$1;

.field final synthetic val$bitmap:Landroid/graphics/Bitmap;


# direct methods
.method constructor <init>(Lolive/app/OliveArt$1;Landroid/graphics/Bitmap;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 79
    iput-object p1, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iput-object p2, p0, Lolive/app/OliveArt$1$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 81
    iget-object v0, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v0, v0, Lolive/app/OliveArt$1;->val$view:Landroid/widget/ImageView;

    iget-object v1, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v1, v1, Lolive/app/OliveArt$1;->val$activity:Landroid/app/Activity;

    const-string v2, "olive_art_loading"

    invoke-static {v1, v2}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/widget/ImageView;->setTag(ILjava/lang/Object;)V

    .line 82
    iget-object v0, p0, Lolive/app/OliveArt$1$1;->val$bitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    .line 83
    invoke-static {}, Lolive/app/OliveArt;->access$000()Landroid/util/LruCache;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v1, v1, Lolive/app/OliveArt$1;->val$url:Ljava/lang/String;

    iget-object v2, p0, Lolive/app/OliveArt$1$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1, v2}, Landroid/util/LruCache;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 84
    iget-object v0, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v0, v0, Lolive/app/OliveArt$1;->val$view:Landroid/widget/ImageView;

    iget-object v1, p0, Lolive/app/OliveArt$1$1;->val$bitmap:Landroid/graphics/Bitmap;

    iget-object v2, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v2, v2, Lolive/app/OliveArt$1;->val$url:Ljava/lang/String;

    invoke-static {v0, v1, v2}, Lolive/app/OliveArt;->access$100(Landroid/widget/ImageView;Landroid/graphics/Bitmap;Ljava/lang/String;)V

    .line 88
    :goto_0
    return-void

    .line 86
    :cond_0
    iget-object v0, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v0, v0, Lolive/app/OliveArt$1;->val$activity:Landroid/app/Activity;

    iget-object v1, p0, Lolive/app/OliveArt$1$1;->this$0:Lolive/app/OliveArt$1;

    iget-object v1, v1, Lolive/app/OliveArt$1;->val$view:Landroid/widget/ImageView;

    invoke-static {v0, v1}, Lolive/app/OliveArt;->access$200(Landroid/content/Context;Landroid/widget/ImageView;)V

    goto :goto_0
.end method
