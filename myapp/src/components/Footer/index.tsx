import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = 'DFSVVV出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Ant Design Pro',
          title: '懂车帝',
          href: 'https://www.dongchedi.com/',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/DFSVVV',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: '吉林大学',
          href: 'https://www.jlu.edu.cn/',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
