.class Lolive/app/Compat$2;
.super Ljava/lang/Object;
.source "Compat.java"

# interfaces
.implements Landroid/widget/TabHost$OnTabChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/Compat;->setupShell(Landroid/app/TabActivity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$animate:[Z

.field final synthetic val$host:Landroid/app/TabActivity;

.field final synthetic val$search:Landroid/view/View;

.field final synthetic val$tabs:Landroid/widget/TabHost;

.field final synthetic val$title:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Landroid/widget/TabHost;Landroid/app/TabActivity;Landroid/widget/TextView;Landroid/view/View;[Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 187
    iput-object p1, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    iput-object p2, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iput-object p3, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    iput-object p4, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    iput-object p5, p0, Lolive/app/Compat$2;->val$animate:[Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTabChanged(Ljava/lang/String;)V
    .locals 9

    .prologue
    const/4 v1, 0x0

    const/4 v2, 0x0

    const v8, 0x3f75c28f    # 0.96f

    const/high16 v7, 0x3f800000    # 1.0f

    .line 189
    iget-object v0, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    invoke-virtual {v0}, Landroid/widget/TabHost;->getCurrentTabView()Landroid/view/View;

    move-result-object v3

    .line 190
    if-nez v3, :cond_4

    move-object v0, v1

    .line 191
    :goto_0
    iget-object v4, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    if-eqz v4, :cond_0

    if-eqz v0, :cond_0

    .line 192
    iget-object v4, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v0

    invoke-virtual {v4, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 194
    :cond_0
    iget-object v0, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    if-eqz v0, :cond_1

    .line 195
    iget-object v4, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iget-object v5, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    const-string v6, "search"

    invoke-static {v0, v5, v6}, Lolive/app/Compat;->access$100(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    if-eqz v0, :cond_5

    move v0, v2

    :goto_1
    invoke-virtual {v4, v0}, Landroid/view/View;->setVisibility(I)V

    .line 197
    :cond_1
    iget-object v0, p0, Lolive/app/Compat$2;->val$animate:[Z

    aget-boolean v0, v0, v2

    if-eqz v0, :cond_3

    .line 198
    if-nez v3, :cond_6

    .line 199
    :goto_2
    if-eqz v1, :cond_2

    .line 200
    const v0, 0x3ecccccd    # 0.4f

    invoke-virtual {v1, v0}, Landroid/view/View;->setScaleX(F)V

    .line 201
    invoke-virtual {v1}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0x15e

    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 203
    :cond_2
    iget-object v0, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    invoke-virtual {v0}, Landroid/widget/TabHost;->getCurrentView()Landroid/view/View;

    move-result-object v0

    .line 204
    if-eqz v0, :cond_3

    .line 206
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setAlpha(F)V

    .line 207
    invoke-virtual {v0, v8}, Landroid/view/View;->setScaleX(F)V

    .line 208
    invoke-virtual {v0, v8}, Landroid/view/View;->setScaleY(F)V

    .line 209
    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/view/ViewPropertyAnimator;->scaleY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0x3c

    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setStartDelay(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0x12c

    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 212
    :cond_3
    return-void

    .line 190
    :cond_4
    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    const-string v4, "id"

    const-string v5, "tabLabel"

    invoke-static {v0, v4, v5}, Lolive/app/Compat;->access$000(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    goto/16 :goto_0

    .line 195
    :cond_5
    const/16 v0, 0x8

    goto :goto_1

    .line 198
    :cond_6
    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    const-string v1, "id"

    const-string v2, "tabPill"

    invoke-static {v0, v1, v2}, Lolive/app/Compat;->access$000(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    goto :goto_2
.end method
