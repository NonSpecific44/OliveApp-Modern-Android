.class Lolive/app/OliveUi$Adaptive$1;
.super Ljava/lang/Object;
.source "OliveUi.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveUi$Adaptive;->onLayoutChange(Landroid/view/View;IIIIIIII)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveUi$Adaptive;

.field final synthetic val$h:I

.field final synthetic val$w:I


# direct methods
.method constructor <init>(Lolive/app/OliveUi$Adaptive;II)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 340
    iput-object p1, p0, Lolive/app/OliveUi$Adaptive$1;->this$0:Lolive/app/OliveUi$Adaptive;

    iput p2, p0, Lolive/app/OliveUi$Adaptive$1;->val$w:I

    iput p3, p0, Lolive/app/OliveUi$Adaptive$1;->val$h:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 342
    iget-object v0, p0, Lolive/app/OliveUi$Adaptive$1;->this$0:Lolive/app/OliveUi$Adaptive;

    iget v1, p0, Lolive/app/OliveUi$Adaptive$1;->val$w:I

    iget v2, p0, Lolive/app/OliveUi$Adaptive$1;->val$h:I

    invoke-virtual {v0, v1, v2}, Lolive/app/OliveUi$Adaptive;->apply(II)V

    .line 343
    return-void
.end method
