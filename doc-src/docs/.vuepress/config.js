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
                text: 'GitHub', link: 'https://github.com/Enaium/cf4m',
            }
        ]
    }
}