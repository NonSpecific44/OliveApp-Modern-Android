.class Lolive/app/Compat$5;
.super Ljava/lang/Object;
.source "Compat.java"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/Compat;->watchLists(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$lastCount:[I

.field final synthetic val$list:Landroid/widget/ListView;


# direct methods
.method constructor <init>(Landroid/widget/ListView;[I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 380
    iput-object p1, p0, Lolive/app/Compat$5;->val$list:Landroid/widget/ListView;

    iput-object p2, p0, Lolive/app/Compat$5;->val$lastCount:[I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onGlobalLayout()V
    .locals 3

    .prologue
    const/4 v1, 0x0

    .line 382
    iget-object v0, p0, Lolive/app/Compat$5;->val$list:Landroid/widget/ListView;

    invoke-virtual {v0}, Landroid/widget/ListView;->getAdapter()Landroid/widget/ListAdapter;

    move-result-object v0

    if-nez v0, :cond_1

    move v0, v1

    .line 383
    :goto_0
    iget-object v2, p0, Lolive/app/Compat$5;->val$lastCount:[I

    aget v2, v2, v1

    if-nez v2, :cond_0

    if-lez v0, :cond_0

    .line 384
    iget-object v2, p0, Lolive/app/Compat$5;->val$list:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->scheduleLayoutAnimation()V

    .line 385
    iget-object v2, p0, Lolive/app/Compat$5;->val$list:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->invalidate()V

    .line 387
    :cond_0
    iget-object v2, p0, Lolive/app/Compat$5;->val$lastCount:[I

    aput v0, v2, v1

    .line 388
    return-void

    .line 382
    :cond_1
    iget-object v0, p0, Lolive/app/Compat$5;->val$list:Landroid/widget/ListView;

    invoke-virtual {v0}, Landroid/widget/ListView;->getAdapter()Landroid/widget/ListAdapter;

    move-result-object v0

    invoke-interface {v0}, Landroid/widget/ListAdapter;->getCount()I

    move-result v0

    goto :goto_0
.end method
