.class Lolive/app/OliveArtists$2;
.super Ljava/lang/Object;
.source "OliveArtists.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArtists;->bindArt(Landroid/widget/ImageView;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$px:I

.field final synthetic val$url:Ljava/lang/String;

.field final synthetic val$view:Landroid/widget/ImageView;


# direct methods
.method constructor <init>(Ljava/lang/String;ILandroid/widget/ImageView;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 208
    iput-object p1, p0, Lolive/app/OliveArtists$2;->val$url:Ljava/lang/String;

    iput p2, p0, Lolive/app/OliveArtists$2;->val$px:I

    iput-object p3, p0, Lolive/app/OliveArtists$2;->val$view:Landroid/widget/ImageView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 210
    iget-object v0, p0, Lolive/app/OliveArtists$2;->val$url:Ljava/lang/String;

    iget v1, p0, Lolive/app/OliveArtists$2;->val$px:I

    invoke-static {v0, v1}, Lolive/app/OliveArt;->load(Ljava/lang/String;I)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 211
    invoke-static {}, Lolive/app/OliveArtists;->access$100()Landroid/os/Handler;

    move-result-object v1

    new-instance v2, Lolive/app/OliveArtists$2$1;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveArtists$2$1;-><init>(Lolive/app/OliveArtists$2;Landroid/graphics/Bitmap;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 221
    return-void
.end method
