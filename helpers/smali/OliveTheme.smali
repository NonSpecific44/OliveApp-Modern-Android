.class public final Lolive/app/OliveTheme;
.super Ljava/lang/Object;
.source "OliveTheme.java"


# static fields
.field static final COLOURS:Ljava/lang/String; = "appearance_colours"

.field static final COLOURS_OLIVE:Ljava/lang/String; = "olive"

.field private static final COLOUR_LABELS:[Ljava/lang/String;

.field static final PREFS:Ljava/lang/String; = "Olive_Settings"

.field static final THEME:Ljava/lang/String; = "appearance_theme"

.field private static final THEME_LABELS:[Ljava/lang/String;

.field static volatile current:Landroid/content/res/Resources$Theme;


# direct methods
.method static constructor <clinit>()V
    .locals 5

    .prologue
    const/4 v4, 0x2

    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 26
    const/4 v0, 0x3

    new-array v0, v0, [Ljava/lang/String;

    const-string v1, "System default"

    aput-object v1, v0, v2

    const-string v1, "Light"

    aput-object v1, v0, v3

    const-string v1, "Dark"

    aput-object v1, v0, v4

    sput-object v0, Lolive/app/OliveTheme;->THEME_LABELS:[Ljava/lang/String;

    .line 27
    new-array v0, v4, [Ljava/lang/String;

    const-string v1, "Wallpaper colours"

    aput-object v1, v0, v2

    const-string v1, "Olive green"

    aput-object v1, v0, v3

    sput-object v0, Lolive/app/OliveTheme;->COLOUR_LABELS:[Ljava/lang/String;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .prologue
    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 33
    return-void
.end method

.method static synthetic access$000()[Ljava/lang/String;
    .locals 1

    .prologue
    .line 21
    sget-object v0, Lolive/app/OliveTheme;->THEME_LABELS:[Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100()[Ljava/lang/String;
    .locals 1

    .prologue
    .line 21
    sget-object v0, Lolive/app/OliveTheme;->COLOUR_LABELS:[Ljava/lang/String;

    return-object v0
.end method

.method public static apply(Landroid/app/Activity;)V
    .locals 4

    .prologue
    .line 45
    invoke-static {p0}, Lolive/app/OliveTheme;->olive(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 46
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const-string v1, "OliveColors.Olive"

    const-string v2, "style"

    invoke-virtual {p0}, Landroid/app/Activity;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 47
    if-eqz v0, :cond_0

    .line 48
    invoke-virtual {p0}, Landroid/app/Activity;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v1

    const/4 v2, 0x1

    invoke-virtual {v1, v0, v2}, Landroid/content/res/Resources$Theme;->applyStyle(IZ)V

    .line 51
    :cond_0
    invoke-virtual {p0}, Landroid/app/Activity;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    sput-object v0, Lolive/app/OliveTheme;->current:Landroid/content/res/Resources$Theme;

    .line 52
    return-void
.end method

.method public static attachSettings(Landroid/app/Activity;)V
    .locals 6

    .prologue
    const/16 v1, 0x8

    .line 97
    const-string v0, "appearanceThemeRow"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .line 98
    const-string v0, "appearanceColoursRow"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .line 99
    if-eqz v3, :cond_0

    if-nez v4, :cond_1

    .line 147
    :cond_0
    :goto_0
    return-void

    .line 102
    :cond_1
    invoke-static {}, Lolive/app/OliveTheme;->supported()Z

    move-result v0

    if-nez v0, :cond_2

    .line 103
    invoke-virtual {v3, v1}, Landroid/view/View;->setVisibility(I)V

    .line 104
    invoke-virtual {v4, v1}, Landroid/view/View;->setVisibility(I)V

    .line 105
    const-string v0, "appearanceHeader"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 106
    if-eqz v0, :cond_0

    .line 107
    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 111
    :cond_2
    const-string v0, "appearanceThemeValue"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 112
    const-string v1, "appearanceColoursValue"

    invoke-static {p0, v1}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p0, v1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 113
    sget-object v2, Lolive/app/OliveTheme;->THEME_LABELS:[Ljava/lang/String;

    invoke-static {p0}, Lolive/app/OliveTheme;->themeMode(Landroid/content/Context;)I

    move-result v5

    aget-object v2, v2, v5

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 114
    sget-object v5, Lolive/app/OliveTheme;->COLOUR_LABELS:[Ljava/lang/String;

    invoke-static {p0}, Lolive/app/OliveTheme;->olive(Landroid/content/Context;)Z

    move-result v2

    if-eqz v2, :cond_3

    const/4 v2, 0x1

    :goto_1
    aget-object v2, v5, v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 115
    new-instance v1, Lolive/app/OliveTheme$1;

    invoke-direct {v1, p0, v0}, Lolive/app/OliveTheme$1;-><init>(Landroid/app/Activity;Landroid/widget/TextView;)V

    invoke-virtual {v3, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 130
    new-instance v0, Lolive/app/OliveTheme$2;

    invoke-direct {v0, p0}, Lolive/app/OliveTheme$2;-><init>(Landroid/app/Activity;)V

    invoke-virtual {v4, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0

    .line 114
    :cond_3
    const/4 v2, 0x0

    goto :goto_1
.end method

.method public static drawable(Landroid/content/res/Resources;I)Landroid/graphics/drawable/Drawable;
    .locals 1

    .prologue
    .line 65
    sget-object v0, Lolive/app/OliveTheme;->current:Landroid/content/res/Resources$Theme;

    .line 66
    if-eqz v0, :cond_0

    .line 68
    :try_start_0
    invoke-virtual {p0, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v0

    .line 72
    :goto_0
    return-object v0

    .line 69
    :catch_0
    move-exception v0

    .line 72
    :cond_0
    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    goto :goto_0
.end method

.method static olive(Landroid/content/Context;)Z
    .locals 5

    .prologue
    const/4 v0, 0x0

    .line 40
    invoke-static {}, Lolive/app/OliveTheme;->supported()Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "olive"

    const-string v2, "Olive_Settings"

    invoke-virtual {p0, v2, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    const-string v3, "appearance_colours"

    const-string v4, "wallpaper"

    invoke-interface {v2, v3, v4}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 v0, 0x1

    :cond_0
    return v0
.end method

.method public static resumed(Landroid/app/Activity;)V
    .locals 1

    .prologue
    .line 56
    invoke-virtual {p0}, Landroid/app/Activity;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    sput-object v0, Lolive/app/OliveTheme;->current:Landroid/content/res/Resources$Theme;

    .line 57
    return-void
.end method

.method static setOlive(Landroid/app/Activity;Z)V
    .locals 3

    .prologue
    .line 88
    const-string v0, "Olive_Settings"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    const-string v2, "appearance_colours"

    if-eqz p1, :cond_1

    const-string v0, "olive"

    :goto_0
    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 89
    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lolive/app/OliveWidget;->render(Landroid/content/Context;)V

    .line 91
    invoke-virtual {p0}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object p0

    .line 92
    :cond_0
    invoke-virtual {p0}, Landroid/app/Activity;->recreate()V

    .line 93
    return-void

    .line 88
    :cond_1
    const-string v0, "wallpaper"

    goto :goto_0
.end method

.method static setThemeMode(Landroid/content/Context;I)V
    .locals 5

    .prologue
    const/4 v1, 0x2

    const/4 v0, 0x1

    const/4 v2, 0x0

    .line 81
    const-string v3, "Olive_Settings"

    invoke-virtual {p0, v3, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "appearance_theme"

    invoke-interface {v3, v4, p1}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 82
    if-ne p1, v0, :cond_1

    move v1, v0

    .line 84
    :cond_0
    :goto_0
    const-string v0, "uimode"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/UiModeManager;

    invoke-virtual {v0, v1}, Landroid/app/UiModeManager;->setApplicationNightMode(I)V

    .line 85
    return-void

    .line 82
    :cond_1
    if-eq p1, v1, :cond_0

    move v1, v2

    goto :goto_0
.end method

.method static supported()Z
    .locals 2

    .prologue
    .line 36
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1f

    if-lt v0, v1, :cond_0

    const/4 v0, 0x1

    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method static themeMode(Landroid/content/Context;)I
    .locals 3

    .prologue
    const/4 v0, 0x0

    .line 77
    invoke-static {}, Lolive/app/OliveTheme;->supported()Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "Olive_Settings"

    invoke-virtual {p0, v1, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "appearance_theme"

    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    :cond_0
    return v0
.end method
