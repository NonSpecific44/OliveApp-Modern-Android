.class Lolive/app/OliveUpdater$5$1$1;
.super Ljava/lang/Object;
.source "OliveUpdater.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUpdater$5$1;->update(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lolive/app/OliveUpdater$5$1;

.field final synthetic val$percent:I


# direct methods
.method constructor <init>(Lolive/app/OliveUpdater$5$1;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 254
    iput-object p1, p0, Lolive/app/OliveUpdater$5$1$1;->this$1:Lolive/app/OliveUpdater$5$1;

    iput p2, p0, Lolive/app/OliveUpdater$5$1$1;->val$percent:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .prologue
    .line 256
    iget-object v0, p0, Lolive/app/OliveUpdater$5$1$1;->this$1:Lolive/app/OliveUpdater$5$1;

    iget-object v0, v0, Lolive/app/OliveUpdater$5$1;->this$0:Lolive/app/OliveUpdater$5;

    iget-object v0, v0, Lolive/app/OliveUpdater$5;->val$bar:Landroid/widget/ProgressBar;

    iget v1, p0, Lolive/app/OliveUpdater$5$1$1;->val$percent:I

    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 257
    return-void
.end method
