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

.field final synthetic val$shuffle:Landroid/view/View;

.field final synthetic val$tabs:Landroid/widget/TabHost;

.field final synthetic val$title:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Landroid/widget/TabHost;Landroid/app/TabActivity;Landroid/widget/TextView;Landroid/view/View;Landroid/view/View;[Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 192
    iput-object p1, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    iput-object p2, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iput-object p3, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    iput-object p4, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    iput-object p5, p0, Lolive/app/Compat$2;->val$shuffle:Landroid/view/View;

    iput-object p6, p0, Lolive/app/Compat$2;->val$animate:[Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTabChanged(Ljava/lang/String;)V
    .locals 10

    .prologue
    const/4 v1, 0x0

    const/16 v3, 0x8

    const v9, 0x3f75c28f    # 0.96f

    const/4 v2, 0x0

    const/high16 v8, 0x3f800000    # 1.0f

    .line 194
    iget-object v0, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    invoke-virtual {v0}, Landroid/widget/TabHost;->getCurrentTabView()Landroid/view/View;

    move-result-object v4

    .line 195
    if-nez v4, :cond_6

    move-object v0, v1

    .line 196
    :goto_0
    iget-object v5, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    if-eqz v5, :cond_0

    if-eqz v0, :cond_0

    .line 197
    iget-object v5, p0, Lolive/app/Compat$2;->val$title:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v0

    invoke-virtual {v5, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 199
    :cond_0
    iget-object v0, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    if-eqz v0, :cond_1

    .line 200
    iget-object v5, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iget-object v6, p0, Lolive/app/Compat$2;->val$search:Landroid/view/View;

    const-string v7, "search"

    invoke-static {v0, v6, v7}, Lolive/app/Compat;->access$100(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    if-eqz v0, :cond_7

    move v0, v2

    :goto_1
    invoke-virtual {v5, v0}, Landroid/view/View;->setVisibility(I)V

    .line 202
    :cond_1
    iget-object v0, p0, Lolive/app/Compat$2;->val$shuffle:Landroid/view/View;

    if-eqz v0, :cond_3

    .line 203
    iget-object v0, p0, Lolive/app/Compat$2;->val$shuffle:Landroid/view/View;

    iget-object v5, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iget-object v6, p0, Lolive/app/Compat$2;->val$shuffle:Landroid/view/View;

    const-string v7, "shuffleAll"

    invoke-static {v5, v6, v7}, Lolive/app/Compat;->access$100(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v5

    if-eqz v5, :cond_2

    move v3, v2

    :cond_2
    invoke-virtual {v0, v3}, Landroid/view/View;->setVisibility(I)V

    .line 205
    :cond_3
    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    iget-object v3, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    invoke-virtual {v3}, Landroid/widget/TabHost;->getCurrentTab()I

    move-result v3

    invoke-static {v0, v3}, Lolive/app/Compat;->access$200(Landroid/app/TabActivity;I)Landroid/view/View;

    move-result-object v0

    .line 206
    iget-object v3, p0, Lolive/app/Compat$2;->val$animate:[Z

    aget-boolean v2, v3, v2

    if-eqz v2, :cond_5

    .line 207
    if-eqz v0, :cond_8

    move-object v2, v0

    :goto_2
    if-nez v2, :cond_9

    .line 209
    :goto_3
    if-eqz v1, :cond_4

    .line 210
    const v0, 0x3ecccccd    # 0.4f

    invoke-virtual {v1, v0}, Landroid/view/View;->setScaleX(F)V

    .line 211
    invoke-virtual {v1}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v8}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0x15e

    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 213
    :cond_4
    iget-object v0, p0, Lolive/app/Compat$2;->val$tabs:Landroid/widget/TabHost;

    invoke-virtual {v0}, Landroid/widget/TabHost;->getCurrentView()Landroid/view/View;

    move-result-object v0

    .line 214
    if-eqz v0, :cond_5

    .line 216
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setAlpha(F)V

    .line 217
    invoke-virtual {v0, v9}, Landroid/view/View;->setScaleX(F)V

    .line 218
    invoke-virtual {v0, v9}, Landroid/view/View;->setScaleY(F)V

    .line 219
    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v8}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v8}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v8}, Landroid/view/ViewPropertyAnimator;->scaleY(F)Landroid/view/ViewPropertyAnimator;

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

    .line 222
    :cond_5
    return-void

    .line 195
    :cond_6
    iget-object v0, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    const-string v5, "id"

    const-string v6, "tabLabel"

    invoke-static {v0, v5, v6}, Lolive/app/Compat;->access$000(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v4, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    goto/16 :goto_0

    :cond_7
    move v0, v3

    .line 200
    goto/16 :goto_1

    :cond_8
    move-object v2, v4

    .line 207
    goto :goto_2

    .line 208
    :cond_9
    if-eqz v0, :cond_a

    :goto_4
    iget-object v1, p0, Lolive/app/Compat$2;->val$host:Landroid/app/TabActivity;

    const-string v2, "id"

    const-string v3, "tabPill"

    invoke-static {v1, v2, v3}, Lolive/app/Compat;->access$000(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    goto :goto_3

    :cond_a
    move-object v0, v4

    goto :goto_4
.end method
