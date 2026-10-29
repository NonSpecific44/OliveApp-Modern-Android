.class Lolive/app/Compat$3;
.super Ljava/lang/Object;
.source "Compat.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/Compat;->buildRail(Landroid/app/TabActivity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$index:I

.field final synthetic val$tabs:Landroid/widget/TabHost;


# direct methods
.method constructor <init>(Landroid/widget/TabHost;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 258
    iput-object p1, p0, Lolive/app/Compat$3;->val$tabs:Landroid/widget/TabHost;

    iput p2, p0, Lolive/app/Compat$3;->val$index:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2

    .prologue
    .line 260
    iget-object v0, p0, Lolive/app/Compat$3;->val$tabs:Landroid/widget/TabHost;

    iget v1, p0, Lolive/app/Compat$3;->val$index:I

    invoke-virtual {v0, v1}, Landroid/widget/TabHost;->setCurrentTab(I)V

    .line 261
    return-void
.end method
