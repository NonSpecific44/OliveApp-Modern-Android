.class Lolive/app/OliveNet$1;
.super Ljava/lang/Object;
.source "OliveNet.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


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


# direct methods
.method constructor <init>(Landroid/app/Activity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 60
    iput-object p1, p0, Lolive/app/OliveNet$1;->val$picker:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    .prologue
    .line 62
    iget-object v0, p0, Lolive/app/OliveNet$1;->val$picker:Landroid/app/Activity;

    invoke-static {v0}, Lolive/app/OliveNet;->access$000(Landroid/app/Activity;)V

    .line 63
    return-void
.end method
