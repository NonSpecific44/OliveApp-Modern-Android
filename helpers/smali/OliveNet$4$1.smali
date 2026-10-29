.class Lolive/app/OliveNet$4$1;
.super Ljava/lang/Object;
.source "OliveNet.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveNet$4;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lolive/app/OliveNet$4;

.field final synthetic val$name:Ljava/lang/String;


# direct methods
.method constructor <init>(Lolive/app/OliveNet$4;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 225
    iput-object p1, p0, Lolive/app/OliveNet$4$1;->this$0:Lolive/app/OliveNet$4;

    iput-object p2, p0, Lolive/app/OliveNet$4$1;->val$name:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 227
    iget-object v0, p0, Lolive/app/OliveNet$4$1;->this$0:Lolive/app/OliveNet$4;

    iget-object v0, v0, Lolive/app/OliveNet$4;->val$picker:Landroid/app/Activity;

    iget-object v1, p0, Lolive/app/OliveNet$4$1;->val$name:Ljava/lang/String;

    iget-object v2, p0, Lolive/app/OliveNet$4$1;->this$0:Lolive/app/OliveNet$4;

    iget-object v2, v2, Lolive/app/OliveNet$4;->val$host:Ljava/lang/String;

    invoke-static {v0, v1, v2}, Lolive/app/OliveNet;->addResult(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;)V

    .line 228
    return-void
.end method
