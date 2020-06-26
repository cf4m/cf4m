module.exports = {
    base: "/cf4m/",
    title: 'CF4M',
    description: 'CF4M Doc',
    dest: '../docs',
    themeConfig: {
        nav: [
            { text: 'Home', link: '/' },
			{
                text: 'Languages',
                items: [
                    { text: 'English', link: '/' },
                    { text: 'Chinese', link: '/zh/' }
                ],
            },
            {
                text: 'Video',
                items: [
                    { text: 'Youtube', link: 'https://www.youtube.com/watch?v=zhfln_BeGh8' },
                    { text: 'BiliBili', link: 'https://www.bilibili.com/video/BV1qK41157gP' }
                ],
            },
            {
                text: 'GitHub', link: 'https://github.com/cf4m/cf4m',
            }
        ]
    }
}