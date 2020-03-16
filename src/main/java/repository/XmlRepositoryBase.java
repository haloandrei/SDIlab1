package repository;

import domain.BaseEntity;
import domain.validators.ValidatorException;

import java.util.Optional;

public abstract class XmlRepositoryBase<T> {
        public virtual XElement ParentElement { get; protected set; }

        protected XName ElementName { get; private set; }

        protected abstract Func<XElement, T> Selector { get; }

    protected XmlRepositoryBase(XName elementName)
        {
            ElementName = elementName;

            // clears the "cached" ParentElement to allow hot file changes
            XDocumentProvider.Default.CurrentDocumentChanged += (sender, eventArgs) => ParentElement = null;
        }

        protected abstract void SetXElementValue(T model, XElement element);

        protected abstract XElement CreateXElement(T model);

        protected abstract object GetEntityId(T entidade);


        public T GetByKey(object keyValue)
        {
            // I intend to remove this magic string "Id"
            return XDocumentProvider.Default.GetDocument().Descendants(ElementName)
                    .Where(e => e.Attribute("Id").Value == keyValue.ToString())
            .Select(Selector)
                .FirstOrDefault();
        }

        public IEnumerable<T> GetAll()
        {
            return ParentElement.Elements(ElementName).Select(Selector);
        }

        public virtual IEnumerable<T> GetAll(object parentId)
        {
            throw new InvalidOperationException("This entity doesn't contains a parent.");
        }

        public virtual void Insert(T entity, bool autoPersist = true)
        {
            ParentElement.Add(CreateXElement(entity));

            if (autoPersist)
                Save();
        }

        public virtual void Update(T entity, bool autoPersist= true)
        {
            // I intend to remove this magic string "Id"
            SetXElementValue(
                    entity,
                    ParentElement.Elements().FirstOrDefault(a => a.Attribute("Id").Value == GetEntityId(entity).ToString()
            ));

            if (persistir)
                Save();
        }

        public virtual void Delete(T entity, bool autoPersist = true)
        {
            ParentElement.Elements().FirstOrDefault(a => a.Attribute("Id").Value == GetEntityId(entity).ToString()).Remove();

            if (autoPersist)
                Save();
        }


        public virtual void Save()
        {
            XDocumentProvider.Default.Save();
        }

}
