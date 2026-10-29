.class Lolive/app/OliveNet$2;
.super Ljava/lang/Object;
.source "OliveNet.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveNet;->setupPicker(Landroid/app/Activity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$picker:Landroid/app/Activity;

.field final synthetic val$subnets:Ljava/util/List;


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 69
    iput-object p1, p0, Lolive/app/OliveNet$2;->val$picker:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveNet$2;->val$subnets:Ljava/util/List;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 71
    iget-object v0, p0, Lolive/app/OliveNet$2;->val$picker:Landroid/app/Activity;

    iget-object v1, p0, Lolive/app/OliveNet$2;->val$subnets:Ljava/util/List;

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Lolive/app/OliveNet;->scan(Landroid/app/Activity;Ljava/util/List;Z)V

    .line 72
    return-void
.end method
