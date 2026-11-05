.class Lolive/app/OliveArt$1;
.super Ljava/lang/Object;
.source "OliveArt.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArt;->show(Landroid/app/Activity;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$url:Ljava/lang/String;

.field final synthetic val$view:Landroid/widget/ImageView;


# direct methods
.method constructor <init>(Ljava/lang/String;Landroid/app/Activity;Landroid/widget/ImageView;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 76
    iput-object p1, p0, Lolive/app/OliveArt$1;->val$url:Ljava/lang/String;

    iput-object p2, p0, Lolive/app/OliveArt$1;->val$activity:Landroid/app/Activity;

    iput-object p3, p0, Lolive/app/OliveArt$1;->val$view:Landroid/widget/ImageView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 78
    iget-object v0, p0, Lolive/app/OliveArt$1;->val$url:Ljava/lang/String;

    const/high16 v1, 0x42c00000    # 96.0f

    iget-object v2, p0, Lolive/app/OliveArt$1;->val$activity:Landroid/app/Activity;

    invoke-virtual {v2}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    iget v2, v2, Landroid/util/DisplayMetrics;->density:F

    mul-float/2addr v1, v2

    invoke-static {v1}, Ljava/lang/Math;->round(F)I

    move-result v1

    invoke-static {v0, v1}, Lolive/app/OliveArt;->load(Ljava/lang/String;I)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 79
    invoke-static {}, Lolive/app/OliveArt;->access$300()Landroid/os/Handler;

    move-result-object v1

    new-instance v2, Lolive/app/OliveArt$1$1;

    invoke-direct {v2, p0, v0}, Lolive/app/OliveArt$1$1;-><init>(Lolive/app/OliveArt$1;Landroid/graphics/Bitmap;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 90
    return-void
.end method
