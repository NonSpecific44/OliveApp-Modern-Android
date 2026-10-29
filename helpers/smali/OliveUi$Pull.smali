.class final Lolive/app/OliveUi$Pull;
.super Ljava/lang/Object;
.source "OliveUi.java"

# interfaces
.implements Landroid/view/View$OnTouchListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveUi;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "Pull"
.end annotation


# instance fields
.field private final activity:Landroid/app/Activity;

.field private final arrow:Landroid/view/View;

.field private final canRefresh:Z

.field private distance:F

.field private final hiddenY:F

.field private final indicator:Landroid/view/View;

.field private final list:Landroid/widget/ListView;

.field loading:Z

.field private final main:Landroid/os/Handler;

.field private pulling:Z

.field private final slop:I

.field private final spinner:Landroid/view/View;

.field private startY:F

.field private final threshold:F

.field private final timeout:Ljava/lang/Runnable;


# direct methods
.method private constructor <init>(Landroid/app/Activity;Landroid/widget/ListView;Landroid/view/View;)V
    .locals 2

    .prologue
    .line 139
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 127
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v0, p0, Lolive/app/OliveUi$Pull;->main:Landroid/os/Handler;

    .line 133
    new-instance v0, Lolive/app/OliveUi$Pull$1;

    invoke-direct {v0, p0}, Lolive/app/OliveUi$Pull$1;-><init>(Lolive/app/OliveUi$Pull;)V

    iput-object v0, p0, Lolive/app/OliveUi$Pull;->timeout:Ljava/lang/Runnable;

    .line 140
    iput-object p1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    .line 141
    iput-object p2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    .line 142
    iput-object p3, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    .line 143
    const-string v0, "pullArrow"

    invoke-static {p1, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    .line 144
    const-string v0, "pullSpinner"

    invoke-static {p1, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p3, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveUi$Pull;->spinner:Landroid/view/View;

    .line 145
    const-string v0, "refresh"

    invoke-direct {p0, v0}, Lolive/app/OliveUi$Pull;->menuItem(Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    if-eqz v0, :cond_1

    const/4 v0, 0x1

    :goto_0
    iput-boolean v0, p0, Lolive/app/OliveUi$Pull;->canRefresh:Z

    .line 146
    const/high16 v0, 0x42900000    # 72.0f

    invoke-static {p1, v0}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v0

    neg-float v0, v0

    iput v0, p0, Lolive/app/OliveUi$Pull;->hiddenY:F

    .line 147
    const/high16 v0, 0x42a00000    # 80.0f

    invoke-static {p1, v0}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v0

    iput v0, p0, Lolive/app/OliveUi$Pull;->threshold:F

    .line 148
    invoke-static {p1}, Landroid/view/ViewConfiguration;->get(Landroid/content/Context;)Landroid/view/ViewConfiguration;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewConfiguration;->getScaledTouchSlop()I

    move-result v0

    iput v0, p0, Lolive/app/OliveUi$Pull;->slop:I

    .line 149
    iget v0, p0, Lolive/app/OliveUi$Pull;->hiddenY:F

    invoke-virtual {p3, v0}, Landroid/view/View;->setTranslationY(F)V

    .line 150
    const/4 v0, 0x0

    invoke-virtual {p3, v0}, Landroid/view/View;->setAlpha(F)V

    .line 151
    iget-boolean v0, p0, Lolive/app/OliveUi$Pull;->canRefresh:Z

    if-eqz v0, :cond_0

    .line 152
    invoke-virtual {p2, p0}, Landroid/widget/ListView;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 154
    :cond_0
    return-void

    .line 145
    :cond_1
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private atTop()Z
    .locals 4

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    .line 193
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->getChildCount()I

    move-result v2

    if-nez v2, :cond_1

    .line 196
    :cond_0
    :goto_0
    return v0

    :cond_1
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-virtual {v2}, Landroid/widget/ListView;->getFirstVisiblePosition()I

    move-result v2

    if-nez v2, :cond_2

    iget-object v2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-virtual {v2, v1}, Landroid/widget/ListView;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    invoke-virtual {v2}, Landroid/view/View;->getTop()I

    move-result v2

    iget-object v3, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-virtual {v3}, Landroid/widget/ListView;->getPaddingTop()I

    move-result v3

    if-ge v2, v3, :cond_0

    :cond_2
    move v0, v1

    goto :goto_0
.end method

.method static attach(Landroid/app/Activity;)Lolive/app/OliveUi$Pull;
    .locals 7

    .prologue
    const/4 v3, 0x0

    .line 158
    const-string v0, "pullIndicator"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .line 159
    if-nez v4, :cond_1

    .line 179
    :cond_0
    :goto_0
    return-object v3

    .line 162
    :cond_1
    const-string v0, "olive_pull"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v5

    .line 163
    invoke-virtual {v4, v5}, Landroid/view/View;->getTag(I)Ljava/lang/Object;

    move-result-object v0

    .line 164
    instance-of v1, v0, Lolive/app/OliveUi$Pull;

    if-eqz v1, :cond_2

    .line 165
    check-cast v0, Lolive/app/OliveUi$Pull;

    move-object v3, v0

    goto :goto_0

    .line 167
    :cond_2
    invoke-virtual {v4}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    .line 169
    const/4 v2, 0x0

    move-object v1, v3

    :goto_1
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v6

    if-ge v2, v6, :cond_4

    if-nez v1, :cond_4

    .line 170
    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v6

    instance-of v6, v6, Landroid/widget/ListView;

    if-eqz v6, :cond_3

    .line 171
    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ListView;

    .line 169
    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 174
    :cond_4
    if-eqz v1, :cond_0

    .line 177
    new-instance v3, Lolive/app/OliveUi$Pull;

    invoke-direct {v3, p0, v1, v4}, Lolive/app/OliveUi$Pull;-><init>(Landroid/app/Activity;Landroid/widget/ListView;Landroid/view/View;)V

    .line 178
    invoke-virtual {v4, v5, v3}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    goto :goto_0
.end method

.method private drag(F)V
    .locals 7

    .prologue
    const/high16 v0, 0x3f800000    # 1.0f

    .line 247
    const/high16 v1, 0x3f000000    # 0.5f

    mul-float/2addr v1, p1

    iput v1, p0, Lolive/app/OliveUi$Pull;->distance:F

    .line 248
    iget v1, p0, Lolive/app/OliveUi$Pull;->distance:F

    iget v2, p0, Lolive/app/OliveUi$Pull;->threshold:F

    div-float/2addr v1, v2

    invoke-static {v0, v1}, Ljava/lang/Math;->min(FF)F

    move-result v1

    .line 249
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    iget v3, p0, Lolive/app/OliveUi$Pull;->hiddenY:F

    iget v4, p0, Lolive/app/OliveUi$Pull;->distance:F

    iget v5, p0, Lolive/app/OliveUi$Pull;->threshold:F

    const v6, 0x3fb33333    # 1.4f

    mul-float/2addr v5, v6

    invoke-static {v4, v5}, Ljava/lang/Math;->min(FF)F

    move-result v4

    add-float/2addr v3, v4

    iget-object v4, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    const/high16 v5, 0x41000000    # 8.0f

    invoke-static {v4, v5}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v4

    add-float/2addr v3, v4

    invoke-virtual {v2, v3}, Landroid/view/View;->setTranslationY(F)V

    .line 250
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    const/high16 v3, 0x3fc00000    # 1.5f

    mul-float/2addr v3, v1

    invoke-static {v0, v3}, Ljava/lang/Math;->min(FF)F

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->setAlpha(F)V

    .line 251
    const v2, 0x3f19999a    # 0.6f

    const v3, 0x3ecccccd    # 0.4f

    mul-float/2addr v3, v1

    add-float/2addr v2, v3

    .line 252
    iget-object v3, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    invoke-virtual {v3, v2}, Landroid/view/View;->setScaleX(F)V

    .line 253
    iget-object v3, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    invoke-virtual {v3, v2}, Landroid/view/View;->setScaleY(F)V

    .line 254
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    if-eqz v2, :cond_0

    .line 255
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Landroid/view/View;->setVisibility(I)V

    .line 256
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    const/high16 v3, 0x43870000    # 270.0f

    mul-float/2addr v3, v1

    invoke-virtual {v2, v3}, Landroid/view/View;->setRotation(F)V

    .line 257
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    cmpl-float v1, v1, v0

    if-ltz v1, :cond_2

    :goto_0
    invoke-virtual {v2, v0}, Landroid/view/View;->setAlpha(F)V

    .line 259
    :cond_0
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->spinner:Landroid/view/View;

    if-eqz v0, :cond_1

    .line 260
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->spinner:Landroid/view/View;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 262
    :cond_1
    return-void

    .line 257
    :cond_2
    const v0, 0x3f0ccccd    # 0.55f

    goto :goto_0
.end method

.method private menuItem(Ljava/lang/String;)Landroid/view/MenuItem;
    .locals 3

    .prologue
    .line 184
    :try_start_0
    new-instance v0, Landroid/widget/PopupMenu;

    iget-object v1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    iget-object v2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-direct {v0, v1, v2}, Landroid/widget/PopupMenu;-><init>(Landroid/content/Context;Landroid/view/View;)V

    invoke-virtual {v0}, Landroid/widget/PopupMenu;->getMenu()Landroid/view/Menu;

    move-result-object v0

    .line 185
    iget-object v1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    invoke-virtual {v1, v0}, Landroid/app/Activity;->onCreateOptionsMenu(Landroid/view/Menu;)Z

    .line 186
    iget-object v1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    invoke-static {v1, p1}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v1

    invoke-interface {v0, v1}, Landroid/view/Menu;->findItem(I)Landroid/view/MenuItem;
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v0

    .line 188
    :goto_0
    return-object v0

    .line 187
    :catch_0
    move-exception v0

    .line 188
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private refresh()V
    .locals 2

    .prologue
    .line 265
    invoke-virtual {p0}, Lolive/app/OliveUi$Pull;->startLoading()V

    .line 266
    const-string v0, "refresh"

    invoke-direct {p0, v0}, Lolive/app/OliveUi$Pull;->menuItem(Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    .line 267
    if-eqz v0, :cond_0

    .line 268
    iget-object v1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    invoke-virtual {v1, v0}, Landroid/app/Activity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    .line 270
    :cond_0
    return-void
.end method

.method private retract()V
    .locals 4

    .prologue
    const v3, 0x3f19999a    # 0.6f

    const/4 v2, 0x0

    .line 293
    iput v2, p0, Lolive/app/OliveUi$Pull;->distance:F

    .line 294
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    iget v1, p0, Lolive/app/OliveUi$Pull;->hiddenY:F

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->translationY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/view/ViewPropertyAnimator;->scaleY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0xfa

    .line 295
    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 296
    return-void
.end method


# virtual methods
.method finishLoading()V
    .locals 2

    .prologue
    .line 287
    const/4 v0, 0x0

    iput-boolean v0, p0, Lolive/app/OliveUi$Pull;->loading:Z

    .line 288
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->main:Landroid/os/Handler;

    iget-object v1, p0, Lolive/app/OliveUi$Pull;->timeout:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 289
    invoke-direct {p0}, Lolive/app/OliveUi$Pull;->retract()V

    .line 290
    return-void
.end method

.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 5

    .prologue
    const/4 v4, 0x0

    const/4 v1, 0x1

    const/4 v0, 0x0

    .line 200
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v2

    packed-switch v2, :pswitch_data_0

    .line 241
    iget-boolean v0, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    :cond_0
    :goto_0
    return v0

    .line 202
    :pswitch_0
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v1

    iput v1, p0, Lolive/app/OliveUi$Pull;->startY:F

    .line 203
    iput-boolean v0, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    goto :goto_0

    .line 206
    :pswitch_1
    iget-boolean v2, p0, Lolive/app/OliveUi$Pull;->loading:Z

    if-nez v2, :cond_0

    .line 209
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v2

    iget v3, p0, Lolive/app/OliveUi$Pull;->startY:F

    sub-float/2addr v2, v3

    .line 210
    iget-boolean v3, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    if-nez v3, :cond_1

    .line 211
    iget v3, p0, Lolive/app/OliveUi$Pull;->slop:I

    int-to-float v3, v3

    cmpl-float v3, v2, v3

    if-lez v3, :cond_2

    invoke-direct {p0}, Lolive/app/OliveUi$Pull;->atTop()Z

    move-result v3

    if-eqz v3, :cond_2

    .line 212
    iput-boolean v1, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    .line 213
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v0

    iput v0, p0, Lolive/app/OliveUi$Pull;->startY:F

    .line 214
    invoke-static {p2}, Landroid/view/MotionEvent;->obtain(Landroid/view/MotionEvent;)Landroid/view/MotionEvent;

    move-result-object v0

    .line 215
    const/4 v2, 0x3

    invoke-virtual {v0, v2}, Landroid/view/MotionEvent;->setAction(I)V

    .line 216
    iget-object v2, p0, Lolive/app/OliveUi$Pull;->list:Landroid/widget/ListView;

    invoke-virtual {v2, v0}, Landroid/widget/ListView;->onTouchEvent(Landroid/view/MotionEvent;)Z

    .line 217
    invoke-virtual {v0}, Landroid/view/MotionEvent;->recycle()V

    .line 218
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->cancel()V

    .line 226
    :cond_1
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v0

    iget v2, p0, Lolive/app/OliveUi$Pull;->startY:F

    sub-float/2addr v0, v2

    invoke-static {v4, v0}, Ljava/lang/Math;->max(FF)F

    move-result v0

    invoke-direct {p0, v0}, Lolive/app/OliveUi$Pull;->drag(F)V

    move v0, v1

    .line 227
    goto :goto_0

    .line 220
    :cond_2
    cmpg-float v1, v2, v4

    if-gez v1, :cond_0

    .line 221
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getRawY()F

    move-result v1

    iput v1, p0, Lolive/app/OliveUi$Pull;->startY:F

    goto :goto_0

    .line 230
    :pswitch_2
    iget-boolean v2, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    if-eqz v2, :cond_0

    .line 233
    iput-boolean v0, p0, Lolive/app/OliveUi$Pull;->pulling:Z

    .line 234
    invoke-virtual {p2}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    if-ne v0, v1, :cond_3

    iget v0, p0, Lolive/app/OliveUi$Pull;->distance:F

    iget v2, p0, Lolive/app/OliveUi$Pull;->threshold:F

    cmpl-float v0, v0, v2

    if-ltz v0, :cond_3

    .line 235
    invoke-direct {p0}, Lolive/app/OliveUi$Pull;->refresh()V

    :goto_1
    move v0, v1

    .line 239
    goto :goto_0

    .line 237
    :cond_3
    invoke-direct {p0}, Lolive/app/OliveUi$Pull;->retract()V

    goto :goto_1

    .line 200
    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
        :pswitch_2
        :pswitch_1
        :pswitch_2
    .end packed-switch
.end method

.method startLoading()V
    .locals 5

    .prologue
    const/high16 v4, 0x3f800000    # 1.0f

    .line 273
    const/4 v0, 0x1

    iput-boolean v0, p0, Lolive/app/OliveUi$Pull;->loading:Z

    .line 274
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->main:Landroid/os/Handler;

    iget-object v1, p0, Lolive/app/OliveUi$Pull;->timeout:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 275
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->main:Landroid/os/Handler;

    iget-object v1, p0, Lolive/app/OliveUi$Pull;->timeout:Ljava/lang/Runnable;

    const-wide/16 v2, 0x7530

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 276
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 277
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->arrow:Landroid/view/View;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 279
    :cond_0
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->spinner:Landroid/view/View;

    if-eqz v0, :cond_1

    .line 280
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->spinner:Landroid/view/View;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 282
    :cond_1
    iget-object v0, p0, Lolive/app/OliveUi$Pull;->indicator:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    iget-object v1, p0, Lolive/app/OliveUi$Pull;->activity:Landroid/app/Activity;

    const/high16 v2, 0x41800000    # 16.0f

    invoke-static {v1, v2}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->translationY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v4}, Landroid/view/ViewPropertyAnimator;->scaleY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0xfa

    .line 283
    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 284
    return-void
.end method
