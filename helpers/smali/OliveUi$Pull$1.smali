.class Lolive/app/OliveUi$Pull$1;
.super Ljava/lang/Object;
.source "OliveUi.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveUi$Pull;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveUi$Pull;


# direct methods
.method constructor <init>(Lolive/app/OliveUi$Pull;)V
    .locals 0

    .prologue
    .line 133
    iput-object p1, p0, Lolive/app/OliveUi$Pull$1;->this$0:Lolive/app/OliveUi$Pull;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .prologue
    .line 135
    iget-object v0, p0, Lolive/app/OliveUi$Pull$1;->this$0:Lolive/app/OliveUi$Pull;

    invoke-virtual {v0}, Lolive/app/OliveUi$Pull;->finishLoading()V

    .line 136
    return-void
.end method
