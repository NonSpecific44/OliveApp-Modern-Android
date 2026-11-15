.class Lolive/app/OliveArtists$2$1;
.super Ljava/lang/Object;
.source "OliveArtists.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArtists$2;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveArtists$2;

.field final synthetic val$bitmap:Landroid/graphics/Bitmap;


# direct methods
.method constructor <init>(Lolive/app/OliveArtists$2;Landroid/graphics/Bitmap;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 211
    iput-object p1, p0, Lolive/app/OliveArtists$2$1;->this$0:Lolive/app/OliveArtists$2;

    iput-object p2, p0, Lolive/app/OliveArtists$2$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 213
    iget-object v0, p0, Lolive/app/OliveArtists$2$1;->val$bitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    .line 214
    invoke-static {}, Lolive/app/OliveArtists;->access$000()Landroid/util/LruCache;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveArtists$2$1;->this$0:Lolive/app/OliveArtists$2;

    iget-object v1, v1, Lolive/app/OliveArtists$2;->val$url:Ljava/lang/String;

    iget-object v2, p0, Lolive/app/OliveArtists$2$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1, v2}, Landroid/util/LruCache;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 215
    iget-object v0, p0, Lolive/app/OliveArtists$2$1;->this$0:Lolive/app/OliveArtists$2;

    iget-object v0, v0, Lolive/app/OliveArtists$2;->val$url:Ljava/lang/String;

    iget-object v1, p0, Lolive/app/OliveArtists$2$1;->this$0:Lolive/app/OliveArtists$2;

    iget-object v1, v1, Lolive/app/OliveArtists$2;->val$view:Landroid/widget/ImageView;

    invoke-virtual {v1}, Landroid/widget/ImageView;->getTag()Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 216
    iget-object v0, p0, Lolive/app/OliveArtists$2$1;->this$0:Lolive/app/OliveArtists$2;

    iget-object v0, v0, Lolive/app/OliveArtists$2;->val$view:Landroid/widget/ImageView;

    iget-object v1, p0, Lolive/app/OliveArtists$2$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 219
    :cond_0
    return-void
.end method
